package com.guard.pinnedlistlib.pinnedlist;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CompositeBaseAdapter extends BaseAdapter{
	 private static final int INITIAL_CAPACITY = 2;

	    public static class Partition {
	        boolean showIfEmpty;
	        boolean hasHeader;

	        public List<Object> mObject;
	        int count;

	        public Partition(boolean showIfEmpty, boolean hasHeader) {
	            this.showIfEmpty = showIfEmpty;
	            this.hasHeader = hasHeader;
	        }

	        /**
	         * True if the directory should be shown even if no contacts are found.
	         */
	        public boolean getShowIfEmpty() {
	            return showIfEmpty;
	        }

	        public boolean getHasHeader() {
	            return hasHeader;
	        }

	        public boolean isEmpty() {
	            return count == 0;
	        }
	    }

	    private final Context mContext;
	    private ArrayList<Partition> mPartitions;
	    private int mCount = 0;
	    private boolean mCacheValid = true;
	    private boolean mNotificationsEnabled = true;
	    private boolean mNotificationNeeded;

	    public CompositeBaseAdapter(Context context) {
	        this(context, INITIAL_CAPACITY);
	    }

	    public CompositeBaseAdapter(Context context, int initialCapacity) {
	        mContext = context;
	        mPartitions = new ArrayList<Partition>();
	    }

	    public Context getContext() {
	        return mContext;
	    }

	    /**
	     * Registers a partition. The cursor for that partition can be set later.
	     * Partitions should be added in the order they are supposed to appear in the
	     * list.
	     */
	    public void addPartition(boolean showIfEmpty, boolean hasHeader) {
	        addPartition(new Partition(showIfEmpty, hasHeader));
	    }

	    public void addPartition(Partition partition) {
	        mPartitions.add(partition);
	        invalidate();
	        notifyDataSetChanged();
	    }

	    public void addPartition(int location, Partition partition) {
	        mPartitions.add(location, partition);
	        invalidate();
	        notifyDataSetChanged();
	    }

	    public void removePartition(int partitionIndex) {
	        mPartitions.remove(partitionIndex);
	        invalidate();
	        notifyDataSetChanged();
	    }

	    /**
	     * Removes cursors for all partitions.
	     */
	    // TODO: Is this really what this is supposed to do? Just remove the cursors? Not close them?
	    // Not remove the partitions themselves? Isn't this leaking?

	    public void clearPartitions() {
	        invalidate();
	        notifyDataSetChanged();
	    }

	    /**
	     * Closes all cursors and removes all partitions.
	     */
	    public void close() {
	        mPartitions.clear();
	        invalidate();
	        notifyDataSetChanged();
	    }

	    public void setHasHeader(int partitionIndex, boolean flag) {
	        mPartitions.get(partitionIndex).hasHeader = flag;
	        invalidate();
	    }

	    public void setShowIfEmpty(int partitionIndex, boolean flag) {
	        mPartitions.get(partitionIndex).showIfEmpty = flag;
	        invalidate();
	    }

	    public Partition getPartition(int partitionIndex) {
	        return mPartitions.get(partitionIndex);
	    }

	    protected void invalidate() {
	        mCacheValid = false;
	    }

	    public int getPartitionCount() {
	        return mPartitions.size();
	    }

	    protected void ensureCacheValid() {
	        if (mCacheValid) {
	            return;
	        }

	        mCount = 0;
	        for (Partition partition : mPartitions) {
	           
	            int count = partition.mObject != null ? partition.mObject.size() : 0;
	            if (partition.hasHeader) {
	                if (count != 0 || partition.showIfEmpty) {
	                    count++;
	                }
	            }
	            partition.count = count;
	            mCount += count;
	        }

	        mCacheValid = true;
	    }

	    /**
	     * Returns true if the specified partition was configured to have a header.
	     */
	    public boolean hasHeader(int partition) {
	        return mPartitions.get(partition).hasHeader;
	    }

	    /**
	     * Returns the total number of list items in all partitions.
	     */
	    public int getCount() {
	        ensureCacheValid();
	        return mCount;
	    }

	    /**
	     * Returns the cursor for the given partition
	     */
	    public List<Object> getObject(int partition) {
	        return mPartitions.get(partition).mObject;
	    }

	    /**
	     * Changes the cursor for an individual partition.
	     */
	    public void changeObject(int partition,  List<Object> objects) {
	            mPartitions.get(partition).mObject = objects;
	            invalidate();
	            notifyDataSetChanged();
	    }

	    /**
	     * Returns true if the specified partition has no cursor or an empty cursor.
	     */
	    public boolean isPartitionEmpty(int partition) {
	        return mPartitions.get(partition).mObject == null || mPartitions.get(partition).mObject.size() == 0;
	    }

	    /**
	     * Given a list position, returns the index of the corresponding partition.
	     */
	    public int getPartitionForPosition(int position) {
	        ensureCacheValid();
	        int start = 0;
	        for (int i = 0, n = mPartitions.size(); i < n; i++) {
	            int end = start + mPartitions.get(i).count;
	            if (position >= start && position < end) {
	                return i;
	            }
	            start = end;
	        }
	        return -1;
	    }

	    /**
	     * Given a list position, return the offset of the corresponding item in its
	     * partition.  The header, if any, will have offset -1.
	     */
	    public int getOffsetInPartition(int position) {
	        ensureCacheValid();
	        int start = 0;
	        for (Partition partition : mPartitions) {
	            int end = start + partition.count;
	            if (position >= start && position < end) {
	                int offset = position - start;
	                if (partition.hasHeader) {
	                    offset--;
	                }
	                return offset;
	            }
	            start = end;
	        }
	        return -1;
	    }

	    /**
	     * Returns the first list position for the specified partition.
	     */
	    public int getPositionForPartition(int partition) {
	        ensureCacheValid();
	        int position = 0;
	        for (int i = 0; i < partition; i++) {
	            position += mPartitions.get(i).count;
	        }
	        return position;
	    }

	    @Override
	    public int getViewTypeCount() {
	        return getItemViewTypeCount() + 1;
	    }

	    /**
	     * Returns the overall number of item view types across all partitions. An
	     * implementation of this method needs to ensure that the returned count is
	     * consistent with the values returned by {@link #getItemViewType(int,int)}.
	     */
	    public int getItemViewTypeCount() {
	        return 1;
	    }

	    /**
	     * Returns the view type for the list item at the specified position in the
	     * specified partition.
	     */
	    protected int getItemViewType(int partition, int position) {
	        return 1;
	    }

	    @Override
	    public int getItemViewType(int position) {
	        ensureCacheValid();
	        int start = 0;
	        for (int i = 0, n = mPartitions.size(); i < n; i++) {
	            int end = start  + mPartitions.get(i).count;
	            if (position >= start && position < end) {
	                int offset = position - start;
	                if (mPartitions.get(i).hasHeader) {
	                    offset--;
	                }
	                if (offset == -1) {
	                    return IGNORE_ITEM_VIEW_TYPE;
	                } else {
	                    return getItemViewType(i, offset);
	                }
	            }
	            start = end;
	        }

	        throw new ArrayIndexOutOfBoundsException(position);
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ensureCacheValid();
	        int start = 0;
	        for (int i = 0, n = mPartitions.size(); i < n; i++) {
	            int end = start + mPartitions.get(i).count;
	            if (position >= start && position < end) {
	                int offset = position - start;
	                if (mPartitions.get(i).hasHeader) {
	                    offset--;
	                }
	                View view;
	                if (offset == -1) {
	                    view = getHeaderView(i, mPartitions.get(i).mObject, convertView, parent);
	                } else {
	                    if (mPartitions.get(i).mObject.size()<=offset) {
	                        throw new IllegalStateException("Couldn't move cursor to position "
	                                + offset);
	                    }
	                    view = getView(i, mPartitions.get(i).mObject, offset, convertView, parent);
	                }
	                if (view == null) {
	                    throw new NullPointerException("View should not be null, partition: " + i
	                            + " position: " + offset);
	                }
	                return view;
	            }
	            start = end;
	        }

	        throw new ArrayIndexOutOfBoundsException(position);
	    }

	    /**
	     * Returns the header view for the specified partition, creating one if needed.
	     */
	    protected View getHeaderView(int partition, List<Object> objects, View convertView,
	            ViewGroup parent) {
	        View view = convertView != null
	                ? convertView
	                : newHeaderView(mContext, partition, objects, parent);
	        bindHeaderView(view, partition, objects);
	        return view;
	    }

	    /**
	     * Creates the header view for the specified partition.
	     */
	    protected View newHeaderView(Context context, int partition, List<Object> objects,
	            ViewGroup parent) {
	        return null;
	    }

	    /**
	     * Binds the header view for the specified partition.
	     */
	    protected void bindHeaderView(View view, int partition, List<Object> objects) {
	    }

	    /**
	     * Returns an item view for the specified partition, creating one if needed.
	     */
	    protected View getView(int partition, List<Object> objects, int position, View convertView,
	            ViewGroup parent) {
	        View view;
	        if (convertView != null) {
	            view = convertView;
	        } else {
	            view = newView(mContext, partition, objects, position, parent);
	        }
	        bindView(view, partition, objects, position);
	        return view;
	    }

	    /**
	     * Creates an item view for the specified partition and position. Position
	     * corresponds directly to the current cursor position.
	     */
	    protected abstract View newView(Context context, int partition, List<Object> objects, int position,
	            ViewGroup parent);

	    /**
	     * Binds an item view for the specified partition and position. Position
	     * corresponds directly to the current cursor position.
	     */
	    protected abstract void bindView(View v, int partition, List<Object> objects, int position);

	    /**
	     * Returns a pre-positioned cursor for the specified list position.
	     */
	    public Object getItem(int position) {
	        ensureCacheValid();
	        int start = 0;
	        for (Partition mPartition : mPartitions) {
	            int end = start + mPartition.count;
	            if (position >= start && position < end) {
	                int offset = position - start;
	                if (mPartition.hasHeader) {
	                    offset--;
	                }
	                if (offset == -1) {
	                    return null;
	                }
	                return mPartition.mObject.get(offset);
	            }
	            start = end;
	        }
	        return null;
	    }

	    /**
	     * Returns the item ID for the specified list position.
	     */
	    public long getItemId(int position) {
	        return 0;
	    }

	    /**
	     * Returns false if any partition has a header.
	     */
	    @Override
	    public boolean areAllItemsEnabled() {
	        for (Partition mPartition : mPartitions) {
	            if (mPartition.hasHeader) {
	                return false;
	            }
	        }
	        return true;
	    }

	    /**
	     * Returns true for all items except headers.
	     */
	    @Override
	    public boolean isEnabled(int position) {
	        ensureCacheValid();
	        int start = 0;
	        for (int i = 0, n = mPartitions.size(); i < n; i++) {
	            int end = start + mPartitions.get(i).count;
	            if (position >= start && position < end) {
	                int offset = position - start;
	                if (mPartitions.get(i).hasHeader && offset == 0) {
	                    return false;
	                } else {
	                    return isEnabled(i, offset);
	                }
	            }
	            start = end;
	        }

	        return false;
	    }

	    /**
	     * Returns true if the item at the specified offset of the specified
	     * partition is selectable and clickable.
	     */
	    protected boolean isEnabled(int partition, int position) {
	        return true;
	    }

	    /**
	     * Enable or disable data change notifications.  It may be a good idea to
	     * disable notifications before making changes to several partitions at once.
	     */
	    public void setNotificationsEnabled(boolean flag) {
	        mNotificationsEnabled = flag;
	        if (flag && mNotificationNeeded) {
	            notifyDataSetChanged();
	        }
	    }

	    @Override
	    public void notifyDataSetChanged() {
	        if (mNotificationsEnabled) {
	            mNotificationNeeded = false;
	            super.notifyDataSetChanged();
	        } else {
	            mNotificationNeeded = true;
	        }
	    }
}
