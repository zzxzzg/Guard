package com.zzxzzg.retrofitdemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yxwang on 16/7/13.
 */
public class MovieEntity {

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":939925,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","动作","犯罪"],"title":"这个杀手不太冷","casts":[{"alt":"https://movie.douban.com/celebrity/1025182/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/8833.jpg","large":"https://img3.doubanio.com/img/celebrity/large/8833.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/8833.jpg"},"name":"让·雷诺","id":"1025182"},{"alt":"https://movie.douban.com/celebrity/1054454/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/2274.jpg","large":"https://img3.doubanio.com/img/celebrity/large/2274.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/2274.jpg"},"name":"娜塔莉·波特曼","id":"1054454"},{"alt":"https://movie.douban.com/celebrity/1010507/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/104.jpg","large":"https://img3.doubanio.com/img/celebrity/large/104.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/104.jpg"},"name":"加里·奥德曼","id":"1010507"}],"collect_count":907556,"original_title":"Léon","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1031876/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/33301.jpg","large":"https://img3.doubanio.com/img/celebrity/large/33301.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/33301.jpg"},"name":"吕克·贝松","id":"1031876"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p511118051.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p511118051.jpg"},"alt":"https://movie.douban.com/subject/1295644/","id":"1295644"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","爱情"],"title":"阿甘正传","casts":[{"alt":"https://movie.douban.com/celebrity/1054450/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/551.jpg","large":"https://img3.doubanio.com/img/celebrity/large/551.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/551.jpg"},"name":"汤姆·汉克斯","id":"1054450"},{"alt":"https://movie.douban.com/celebrity/1002676/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/51737.jpg","large":"https://img1.doubanio.com/img/celebrity/large/51737.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/51737.jpg"},"name":"罗宾·怀特","id":"1002676"},{"alt":"https://movie.douban.com/celebrity/1031848/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1345.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1345.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1345.jpg"},"name":"加里·西尼斯","id":"1031848"}],"collect_count":822553,"original_title":"Forrest Gump","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1053564/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/505.jpg","large":"https://img3.doubanio.com/img/celebrity/large/505.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/505.jpg"},"name":"罗伯特·泽米吉斯","id":"1053564"}],"year":"1994","images":{"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p510876377.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p510876377.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p510876377.jpg"},"alt":"https://movie.douban.com/subject/1292720/","id":"1292720"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","爱情","同性"],"title":"霸王别姬","casts":[{"alt":"https://movie.douban.com/celebrity/1003494/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/67.jpg","large":"https://img1.doubanio.com/img/celebrity/large/67.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/67.jpg"},"name":"张国荣","id":"1003494"},{"alt":"https://movie.douban.com/celebrity/1050265/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/10381.jpg","large":"https://img3.doubanio.com/img/celebrity/large/10381.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/10381.jpg"},"name":"张丰毅","id":"1050265"},{"alt":"https://movie.douban.com/celebrity/1035641/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1399268395.47.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1399268395.47.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1399268395.47.jpg"},"name":"巩俐","id":"1035641"}],"collect_count":650772,"original_title":"霸王别姬","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1023040/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/750.jpg","large":"https://img3.doubanio.com/img/celebrity/large/750.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/750.jpg"},"name":"陈凯歌","id":"1023040"}],"year":"1993","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p1910813120.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p1910813120.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p1910813120.jpg"},"alt":"https://movie.douban.com/subject/1291546/","id":"1291546"},{"rating":{"max":10,"average":9.5,"stars":"50","min":0},"genres":["剧情","喜剧","爱情"],"title":"美丽人生","casts":[{"alt":"https://movie.douban.com/celebrity/1041004/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/26764.jpg","large":"https://img3.doubanio.com/img/celebrity/large/26764.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/26764.jpg"},"name":"罗伯托·贝尼尼","id":"1041004"},{"alt":"https://movie.douban.com/celebrity/1000375/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/9548.jpg","large":"https://img1.doubanio.com/img/celebrity/large/9548.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/9548.jpg"},"name":"尼可莱塔·布拉斯基","id":"1000375"},{"alt":"https://movie.douban.com/celebrity/1000368/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/45590.jpg","large":"https://img3.doubanio.com/img/celebrity/large/45590.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/45590.jpg"},"name":"乔治·坎塔里尼","id":"1000368"}],"collect_count":445073,"original_title":"La vita è bella","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1041004/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/26764.jpg","large":"https://img3.doubanio.com/img/celebrity/large/26764.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/26764.jpg"},"name":"罗伯托·贝尼尼","id":"1041004"}],"year":"1997","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p510861873.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p510861873.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p510861873.jpg"},"alt":"https://movie.douban.com/subject/1292063/","id":"1292063"},{"rating":{"max":10,"average":9.2,"stars":"45","min":0},"genres":["剧情","动画","奇幻"],"title":"千与千寻","casts":[{"alt":"https://movie.douban.com/celebrity/1023337/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1463193210.13.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1463193210.13.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1463193210.13.jpg"},"name":"柊瑠美","id":"1023337"},{"alt":"https://movie.douban.com/celebrity/1005438/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/44986.jpg","large":"https://img3.doubanio.com/img/celebrity/large/44986.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/44986.jpg"},"name":"入野自由","id":"1005438"},{"alt":"https://movie.douban.com/celebrity/1045797/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/18785.jpg","large":"https://img3.doubanio.com/img/celebrity/large/18785.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/18785.jpg"},"name":"夏木真理","id":"1045797"}],"collect_count":722395,"original_title":"千と千尋の神隠し","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054439/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/616.jpg","large":"https://img3.doubanio.com/img/celebrity/large/616.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/616.jpg"},"name":"宫崎骏","id":"1054439"}],"year":"2001","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p1910830216.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p1910830216.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p1910830216.jpg"},"alt":"https://movie.douban.com/subject/1291561/","id":"1291561"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","历史","战争"],"title":"辛德勒的名单","casts":[{"alt":"https://movie.douban.com/celebrity/1031220/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/44906.jpg","large":"https://img3.doubanio.com/img/celebrity/large/44906.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/44906.jpg"},"name":"连姆·尼森","id":"1031220"},{"alt":"https://movie.douban.com/celebrity/1054393/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/1374649659.58.jpg","large":"https://img1.doubanio.com/img/celebrity/large/1374649659.58.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/1374649659.58.jpg"},"name":"本·金斯利","id":"1054393"},{"alt":"https://movie.douban.com/celebrity/1006956/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/28941.jpg","large":"https://img3.doubanio.com/img/celebrity/large/28941.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/28941.jpg"},"name":"拉尔夫·费因斯","id":"1006956"}],"collect_count":429187,"original_title":"Schindler's List","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054440/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34602.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34602.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34602.jpg"},"name":"史蒂文·斯皮尔伯格","id":"1054440"}],"year":"1993","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p492406163.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p492406163.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p492406163.jpg"},"alt":"https://movie.douban.com/subject/1295124/","id":"1295124"},{"rating":{"max":10,"average":9.2,"stars":"45","min":0},"genres":["剧情","音乐"],"title":"海上钢琴师","casts":[{"alt":"https://movie.douban.com/celebrity/1025176/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/6281.jpg","large":"https://img3.doubanio.com/img/celebrity/large/6281.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/6281.jpg"},"name":"蒂姆·罗斯","id":"1025176"},{"alt":"https://movie.douban.com/celebrity/1010659/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1355152571.6.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1355152571.6.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1355152571.6.jpg"},"name":"普路特·泰勒·文斯","id":"1010659"},{"alt":"https://movie.douban.com/celebrity/1027407/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/12333.jpg","large":"https://img3.doubanio.com/img/celebrity/large/12333.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/12333.jpg"},"name":"比尔·努恩","id":"1027407"}],"collect_count":687614,"original_title":"La leggenda del pianista sull'oceano","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1018983/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/195.jpg","large":"https://img3.doubanio.com/img/celebrity/large/195.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/195.jpg"},"name":"朱塞佩·托纳多雷","id":"1018983"}],"year":"1998","images":{"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p511146957.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p511146957.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p511146957.jpg"},"alt":"https://movie.douban.com/subject/1292001/","id":"1292001"},{"rating":{"max":10,"average":9.3,"stars":"50","min":0},"genres":["喜剧","爱情","科幻"],"title":"机器人总动员","casts":[{"alt":"https://movie.douban.com/celebrity/1009535/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/13028.jpg","large":"https://img1.doubanio.com/img/celebrity/large/13028.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/13028.jpg"},"name":"本·贝尔特","id":"1009535"},{"alt":"https://movie.douban.com/celebrity/1000389/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1365856130.16.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1365856130.16.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1365856130.16.jpg"},"name":"艾丽莎·奈特","id":"1000389"},{"alt":"https://movie.douban.com/celebrity/1018022/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/31068.jpg","large":"https://img1.doubanio.com/img/celebrity/large/31068.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/31068.jpg"},"name":"杰夫·格尔林","id":"1018022"}],"collect_count":542167,"original_title":"WALL·E","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1036450/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1302.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1302.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1302.jpg"},"name":"安德鲁·斯坦顿","id":"1036450"}],"year":"2008","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p449665982.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p449665982.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p449665982.jpg"},"alt":"https://movie.douban.com/subject/2131459/","id":"2131459"},{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","动作","科幻"],"title":"盗梦空间","casts":[{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/470.jpg","large":"https://img3.doubanio.com/img/celebrity/large/470.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/470.jpg"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1101703/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/3517.jpg","large":"https://img1.doubanio.com/img/celebrity/large/3517.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/3517.jpg"},"name":"约瑟夫·高登-莱维特","id":"1101703"},{"alt":"https://movie.douban.com/celebrity/1012520/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/118.jpg","large":"https://img1.doubanio.com/img/celebrity/large/118.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/118.jpg"},"name":"艾伦·佩吉","id":"1012520"}],"collect_count":836392,"original_title":"Inception","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054524/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/673.jpg","large":"https://img3.doubanio.com/img/celebrity/large/673.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/673.jpg"},"name":"克里斯托弗·诺兰","id":"1054524"}],"year":"2010","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p513344864.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p513344864.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p513344864.jpg"},"alt":"https://movie.douban.com/subject/3541415/","id":"3541415"}]
     * title : 豆瓣电影Top250
     */

    @SerializedName("count")
    private int mCount;
    @SerializedName("start")
    private int mStart;
    @SerializedName("total")
    private int mTotal;
    @SerializedName("title")
    private String mTitle;
    /**
     * rating : {"max":10,"average":9.6,"stars":"50","min":0}
     * genres : ["犯罪","剧情"]
     * title : 肖申克的救赎
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
     * collect_count : 939925
     * original_title : The Shawshank Redemption
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
     * year : 1994
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"}
     * alt : https://movie.douban.com/subject/1292052/
     * id : 1292052
     */

    @SerializedName("subjects")
    private List<SubjectsBean> mSubjects;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int start) {
        mStart = start;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<SubjectsBean> getSubjects() {
        return mSubjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        mSubjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * max : 10
         * average : 9.6
         * stars : 50
         * min : 0
         */

        @SerializedName("rating")
        private RatingBean mRating;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("collect_count")
        private int mCollectCount;
        @SerializedName("original_title")
        private String mOriginalTitle;
        @SerializedName("subtype")
        private String mSubtype;
        @SerializedName("year")
        private String mYear;
        /**
         * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
         * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
         * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
         */

        @SerializedName("images")
        private ImagesBean mImages;
        @SerializedName("alt")
        private String mAlt;
        @SerializedName("id")
        private String mId;
        @SerializedName("genres")
        private List<String> mGenres;
        /**
         * alt : https://movie.douban.com/celebrity/1054521/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
         * name : 蒂姆·罗宾斯
         * id : 1054521
         */

        @SerializedName("casts")
        private List<CastsBean> mCasts;
        /**
         * alt : https://movie.douban.com/celebrity/1047973/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"}
         * name : 弗兰克·德拉邦特
         * id : 1047973
         */

        @SerializedName("directors")
        private List<DirectorsBean> mDirectors;

        public RatingBean getRating() {
            return mRating;
        }

        public void setRating(RatingBean rating) {
            mRating = rating;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public int getCollectCount() {
            return mCollectCount;
        }

        public void setCollectCount(int collectCount) {
            mCollectCount = collectCount;
        }

        public String getOriginalTitle() {
            return mOriginalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            mOriginalTitle = originalTitle;
        }

        public String getSubtype() {
            return mSubtype;
        }

        public void setSubtype(String subtype) {
            mSubtype = subtype;
        }

        public String getYear() {
            return mYear;
        }

        public void setYear(String year) {
            mYear = year;
        }

        public ImagesBean getImages() {
            return mImages;
        }

        public void setImages(ImagesBean images) {
            mImages = images;
        }

        public String getAlt() {
            return mAlt;
        }

        public void setAlt(String alt) {
            mAlt = alt;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public List<String> getGenres() {
            return mGenres;
        }

        public void setGenres(List<String> genres) {
            mGenres = genres;
        }

        public List<CastsBean> getCasts() {
            return mCasts;
        }

        public void setCasts(List<CastsBean> casts) {
            mCasts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return mDirectors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            mDirectors = directors;
        }

        public static class RatingBean {
            @SerializedName("max")
            private int mMax;
            @SerializedName("average")
            private double mAverage;
            @SerializedName("stars")
            private String mStars;
            @SerializedName("min")
            private int mMin;

            public int getMax() {
                return mMax;
            }

            public void setMax(int max) {
                mMax = max;
            }

            public double getAverage() {
                return mAverage;
            }

            public void setAverage(double average) {
                mAverage = average;
            }

            public String getStars() {
                return mStars;
            }

            public void setStars(String stars) {
                mStars = stars;
            }

            public int getMin() {
                return mMin;
            }

            public void setMin(int min) {
                mMin = min;
            }
        }

        public static class ImagesBean {
            @SerializedName("small")
            private String mSmall;
            @SerializedName("large")
            private String mLarge;
            @SerializedName("medium")
            private String mMedium;

            public String getSmall() {
                return mSmall;
            }

            public void setSmall(String small) {
                mSmall = small;
            }

            public String getLarge() {
                return mLarge;
            }

            public void setLarge(String large) {
                mLarge = large;
            }

            public String getMedium() {
                return mMedium;
            }

            public void setMedium(String medium) {
                mMedium = medium;
            }
        }

        public static class CastsBean {
            @SerializedName("alt")
            private String mAlt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/17525.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/17525.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/17525.jpg
             */

            @SerializedName("avatars")
            private AvatarsBean mAvatars;
            @SerializedName("name")
            private String mName;
            @SerializedName("id")
            private String mId;

            public String getAlt() {
                return mAlt;
            }

            public void setAlt(String alt) {
                mAlt = alt;
            }

            public AvatarsBean getAvatars() {
                return mAvatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                mAvatars = avatars;
            }

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                mName = name;
            }

            public String getId() {
                return mId;
            }

            public void setId(String id) {
                mId = id;
            }

            public static class AvatarsBean {
                @SerializedName("small")
                private String mSmall;
                @SerializedName("large")
                private String mLarge;
                @SerializedName("medium")
                private String mMedium;

                public String getSmall() {
                    return mSmall;
                }

                public void setSmall(String small) {
                    mSmall = small;
                }

                public String getLarge() {
                    return mLarge;
                }

                public void setLarge(String large) {
                    mLarge = large;
                }

                public String getMedium() {
                    return mMedium;
                }

                public void setMedium(String medium) {
                    mMedium = medium;
                }
            }
        }

        public static class DirectorsBean {
            @SerializedName("alt")
            private String mAlt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/230.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/230.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/230.jpg
             */

            @SerializedName("avatars")
            private AvatarsBean mAvatars;
            @SerializedName("name")
            private String mName;
            @SerializedName("id")
            private String mId;

            public String getAlt() {
                return mAlt;
            }

            public void setAlt(String alt) {
                mAlt = alt;
            }

            public AvatarsBean getAvatars() {
                return mAvatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                mAvatars = avatars;
            }

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                mName = name;
            }

            public String getId() {
                return mId;
            }

            public void setId(String id) {
                mId = id;
            }

            public static class AvatarsBean {
                @SerializedName("small")
                private String mSmall;
                @SerializedName("large")
                private String mLarge;
                @SerializedName("medium")
                private String mMedium;

                public String getSmall() {
                    return mSmall;
                }

                public void setSmall(String small) {
                    mSmall = small;
                }

                public String getLarge() {
                    return mLarge;
                }

                public void setLarge(String large) {
                    mLarge = large;
                }

                public String getMedium() {
                    return mMedium;
                }

                public void setMedium(String medium) {
                    mMedium = medium;
                }
            }
        }
    }
}
