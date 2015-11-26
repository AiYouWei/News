package com.news;

import com.news.activities.AboutUsActivity;
import com.news.activities.ColumnNewsActivity;
import com.news.activities.FeedbackActivity;
import com.news.activities.FontSizeActivity;
import com.news.activities.HomeTabsActivity;
import com.news.activities.ManagerEditActivity;
import com.news.activities.MineCollectionActivity;
import com.news.activities.MineDownLoadActivity;
import com.news.activities.MineMessageActivity;
import com.news.activities.MineSettingActivity;
import com.news.activities.MoreSubjectActivity;
import com.news.activities.NewsCommentActivity;
import com.news.activities.NewsContentActivity;
import com.news.activities.NewsImageActivity;
import com.news.activities.NewsImagesActivity;
import com.news.activities.NewsSearchActivity;
import com.news.activities.ReplyCommentActivity;
import com.news.activities.ScoreIntroActivity;
import com.news.activities.SubjectDetailActivity;
import com.news.activities.UserForgetActivity;
import com.news.activities.UserInfoActivity;
import com.news.activities.UserLoginActivity;
import com.news.activities.UserRegistActivity;
import com.news.activities.VoiceDetailActivity;
import com.news.activities.VoteActivity;
import com.news.fragments.TabColumnFragment;
import com.news.fragments.TabMeFragment;
import com.news.fragments.TabNewsFragment;
import com.news.fragments.TabPaperFragment;
import com.news.fragments.TabVoiceFragment;

import org.androidx.frames.BaseUris;

/**
 * 项目跳转的UI
 *
 * @author slioe shu
 */
public class TYUris extends BaseUris {
    public final static String HOME_TABS = registPageUri(HomeTabsActivity.class); // Tab主页面
    public final static String TAB_NEWS = registNaviUri(TabNewsFragment.class); // 新闻
    public final static String TAB_PAPER = registNaviUri(TabPaperFragment.class); // 报纸
    public final static String TAB_COLUMN = registNaviUri(TabColumnFragment.class); // 专栏
    public final static String TAB_VOICE = registNaviUri(TabVoiceFragment.class); // 民声
    public final static String TAB_ME = registNaviUri(TabMeFragment.class); // 我
    public final static String USER_LOGIN = registPageUri(UserLoginActivity.class); // 用户登录
    public final static String USER_REGIST = registPageUri(UserRegistActivity.class); // 用户注册
    public final static String NEWS_SEARCH = registPageUri(NewsSearchActivity.class); // 新闻搜索
    public final static String MINE_SETTING = registPageUri(MineSettingActivity.class); // 个人设置
    public final static String FONT_SIZE = registPageUri(FontSizeActivity.class); // 字号选择
    public final static String USER_INFO = registPageUri(UserInfoActivity.class); // 我的资料
    public final static String MINE_COLLECTION = registPageUri(MineCollectionActivity.class); // 我的收藏
    public final static String USER_FORGET = registPageUri(UserForgetActivity.class); // 忘记密码
    public final static String MINE_MESSAGE = registPageUri(MineMessageActivity.class); // 我的消息
    public final static String MINE_DOWNLOAD = registPageUri(MineDownLoadActivity.class); // 我的下载
    public final static String ABOUT_US = registPageUri(AboutUsActivity.class); // 关于我们
    public final static String REPLY_COMMENT = registPageUri(ReplyCommentActivity.class); // 评论我的/回复我的
    public final static String MANAGER_EDIT = registPageUri(ManagerEditActivity.class); // 发布全民城管
    public final static String VOICE_DETAIL = registPageUri(VoiceDetailActivity.class); // 民声详情
    public final static String SUBJECT_DETAIL = registPageUri(SubjectDetailActivity.class); // 专题报道
    public final static String NEWS_IMAGE = registPageUri(NewsImageActivity.class); // 新闻详情大图
    public final static String NEWS_IMAGES = registPageUri(NewsImagesActivity.class); // 新闻详情多图
    public final static String NEWS_COMMENT = registPageUri(NewsCommentActivity.class); // 新闻评论
    public final static String COLUMN_NEWS = registPageUri(ColumnNewsActivity.class); // 新闻专题
    public final static String SCORE_INTRO = registPageUri(ScoreIntroActivity.class); // 积分介绍
    public final static String FEEDBACK = registPageUri(FeedbackActivity.class); // 意见反馈
    public final static String NEWS_CONTENT = registPageUri(NewsContentActivity.class); // 新闻栏目
    public final static String MORE_SUBJECT = registPageUri(MoreSubjectActivity.class); // 新闻栏目
    public final static String SUBJECT_VOTE = registPageUri(VoteActivity.class); // 新闻栏目
}
