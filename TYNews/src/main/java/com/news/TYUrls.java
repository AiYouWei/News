package com.news;

/**
 * 网络请求相关
 *
 * @author slioe shu
 */
public class TYUrls {
    public final static String BASE_URL = "http://www.tyrbw.com/epaper2/index.php";

    public final static String WELCOME = BASE_URL + "?r=welcome/ads"; // 启动页和广告页

    /* 新闻 */
    public final static String NEWS_COLUMNS = BASE_URL + "?r=news/columns"; // 新闻栏目
    public final static String NEWS_LISTS = BASE_URL + "?r=news/home"; // 新闻列表
    public final static String NEWS_SEARCH = BASE_URL + "?r=news/search"; // 新闻搜索
    public final static String MULTI_DETAIL = BASE_URL + "?r=news/multi"; // 多图新闻详情
    public final static String NORMAL_DETAIL = BASE_URL + "?r=news/normal"; // 普通新闻详情

    /* 我 */
    public final static String USER_REGIST = BASE_URL + "?r=site/register"; // 用户注册
    public final static String GET_VERIFY = BASE_URL + "?r=site/verify"; // 获取验证码
    public final static String USET_LOGIN = BASE_URL + "?r=site/login"; // 用户登录
    public final static String UPDATE_PIC = BASE_URL + "?r=site/pic"; // 上传头像
    public final static String USER_FORGET = BASE_URL + "?r=site/forget"; // 忘记密码
    public final static String ME_INFO = BASE_URL + "?r=site/basic"; // 用户主页
    public final static String CHECK_IN = BASE_URL + "?r=site/checkin"; // 签到
    public final static String USER_INFO = BASE_URL + "?r=site/info"; // 我的资料
    public final static String UPDATE_INFO = BASE_URL + "?r=site/update"; // 更新资料
    public final static String MINE_COMMENT = BASE_URL + "?r=site/mycoms"; //我的评论
    public final static String APP_UPDATE = BASE_URL + "?r=news/update"; //版本更新
    public final static String SCORE_INTRO = BASE_URL + "?r=site/intro"; //积分规则
    public final static String INVITE_FRIEND = BASE_URL + "?r=site/sharefs"; //邀请好友
    public final static String FEEDBACK = BASE_URL + "?r=feedback/create"; //意见反馈
    public final static String MINE_MSG = BASE_URL + "?r=site/sysmsg"; //系统消息
    public final static String MINE_HEART = BASE_URL + "?r=site/heart&uid="; //心跳消息

    /* 民声 */
    public final static String CLUE_CREATE = BASE_URL + "?r=clue/create"; // 新闻线索-创建
    public final static String CLUE_LIST = BASE_URL + "?r=clue/list"; // 新闻线索-列表
    public final static String CLUE_DETAIL = BASE_URL + "?r=clue/view"; // 新闻线索-详情
    public final static String URBAN_CREATE = BASE_URL + "?r=urban/create"; // 全民城管-创建
    public final static String URBAN_LIST = BASE_URL + "?r=urban/list"; // 全民城管-列表
    public final static String URBAN_DETAIL = BASE_URL + "?r=urban/view"; // 全民城管-详情
    public final static String SUP_CREATE = BASE_URL + "?r=sup/create"; // 舆论监督-创建
    public final static String SUP_LIST = BASE_URL + "?r=sup/list"; // 舆论监督-列表
    public final static String SUP_DETAIL = BASE_URL + "?r=sup/view"; // 舆论监督-详情

    /* 专栏 */
    public final static String COLUMN_CITY = BASE_URL + "?r=news/citys"; // 县区市局
    public final static String COLUMN_LIST = BASE_URL + "?r=news/zhuanti"; // 专题速递
    public final static String COLUMN_NEWS = BASE_URL + "?r=news/zhome"; // 新闻类专题
    public final static String COLUMN_VOTE = BASE_URL + "?r=vote/home"; // 投票类专题
    public final static String COLUMN_NEWSB = BASE_URL + "?r=news/bhome"; // 报道类专题
    public final static String SUBJECT_MORE = BASE_URL + "?r=news/more"; // 报道类专题更多
    public final static String VOTE_PARA = BASE_URL + "?r=vote/para"; //投票分享
    public final static String VOTE_ITEM = BASE_URL + "?r=vote/item_para"; // 投票ITEM分享
}
