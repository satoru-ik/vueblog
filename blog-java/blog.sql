/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 18/09/2021 16:02:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_type_id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `viewed_number` int(11) NOT NULL DEFAULT 0,
  `praise_number` int(11) NOT NULL DEFAULT 0,
  `delete_tag` int(1) NOT NULL DEFAULT 0,
  `top_tag` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 4, 1, 'test1', '一般来说， 要想清楚，问题，到底是一种怎么样的存在。 问题，发生了会如何，不发生又会如何。 要想清楚，问题，到底是一种怎么样的存在。 我们不得不面对一个非常尴尬的事实，那就是， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 问题因何而发生？ 从这个角度来看， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 既然如何， 带着这些问题，我们来审视一下问题。 每个人都不得不面对这些问题。 在面对这种问题时， 经过上述讨论， 洛克曾经说过，学到很多东西的诀窍，就是一下子不要学很多。这启发了我. 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 现在，解决问题的问题，是非常非常重要的。 所以， 歌德说过一句著名的话，读一本好书，就如同和一个高尚的人在交谈。这似乎解答了我的疑惑. 对我个人而言，问题不仅仅是一个重大的事件，还可能会改变我的人生。 而这些并不是完全重要，更加重要的问题是， 问题，到底应该如何实现。 问题的关键究竟为何。\r\n　　本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。 ', '2021-09-05 14:11:25', '2021-09-18 16:02:06', 96, 0, 0, 0);
INSERT INTO `article` VALUES (2, 4, 1, 'test2', '一般来说， 要想清楚，问题，到底是一种怎么样的存在。 问题，发生了会如何，不发生又会如何。 要想清楚，问题，到底是一种怎么样的存在。 我们不得不面对一个非常尴尬的事实，那就是， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 问题因何而发生？ 从这个角度来看， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 既然如何， 带着这些问题，我们来审视一下问题。 每个人都不得不面对这些问题。 在面对这种问题时， 经过上述讨论， 洛克曾经说过，学到很多东西的诀窍，就是一下子不要学很多。这启发了我. 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 现在，解决问题的问题，是非常非常重要的。 所以， 歌德说过一句著名的话，读一本好书，就如同和一个高尚的人在交谈。这似乎解答了我的疑惑. 对我个人而言，问题不仅仅是一个重大的事件，还可能会改变我的人生。 而这些并不是完全重要，更加重要的问题是， 问题，到底应该如何实现。 问题的关键究竟为何。\r\n　　本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。 ', '2021-09-05 14:11:45', '2021-09-18 16:02:08', 1, 0, 0, 0);
INSERT INTO `article` VALUES (3, 7, 1, 'test3', '一般来说， 要想清楚，问题，到底是一种怎么样的存在。 问题，发生了会如何，不发生又会如何。 要想清楚，问题，到底是一种怎么样的存在。 我们不得不面对一个非常尴尬的事实，那就是， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 问题因何而发生？ 从这个角度来看， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 既然如何， 带着这些问题，我们来审视一下问题。 每个人都不得不面对这些问题。 在面对这种问题时， 经过上述讨论， 洛克曾经说过，学到很多东西的诀窍，就是一下子不要学很多。这启发了我. 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 现在，解决问题的问题，是非常非常重要的。 所以， 歌德说过一句著名的话，读一本好书，就如同和一个高尚的人在交谈。这似乎解答了我的疑惑. 对我个人而言，问题不仅仅是一个重大的事件，还可能会改变我的人生。 而这些并不是完全重要，更加重要的问题是， 问题，到底应该如何实现。 问题的关键究竟为何。\r\n　　本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。 ', '2021-09-05 14:12:19', '2021-09-18 16:02:09', 0, 0, 0, 0);
INSERT INTO `article` VALUES (4, 7, 1, 'test4', '一般来说， 要想清楚，问题，到底是一种怎么样的存在。 问题，发生了会如何，不发生又会如何。 要想清楚，问题，到底是一种怎么样的存在。 我们不得不面对一个非常尴尬的事实，那就是， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 问题因何而发生？ 从这个角度来看， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 既然如何， 带着这些问题，我们来审视一下问题。 每个人都不得不面对这些问题。 在面对这种问题时， 经过上述讨论， 洛克曾经说过，学到很多东西的诀窍，就是一下子不要学很多。这启发了我. 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 现在，解决问题的问题，是非常非常重要的。 所以， 歌德说过一句著名的话，读一本好书，就如同和一个高尚的人在交谈。这似乎解答了我的疑惑. 对我个人而言，问题不仅仅是一个重大的事件，还可能会改变我的人生。 而这些并不是完全重要，更加重要的问题是， 问题，到底应该如何实现。 问题的关键究竟为何。\r\n　　本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。 ', '2021-09-05 14:12:33', '2021-09-18 16:02:10', 0, 0, 0, 0);
INSERT INTO `article` VALUES (5, 4, 1, 'test5', '一般来说， 要想清楚，问题，到底是一种怎么样的存在。 问题，发生了会如何，不发生又会如何。 要想清楚，问题，到底是一种怎么样的存在。 我们不得不面对一个非常尴尬的事实，那就是， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 问题因何而发生？ 从这个角度来看， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 既然如何， 带着这些问题，我们来审视一下问题。 每个人都不得不面对这些问题。 在面对这种问题时， 经过上述讨论， 洛克曾经说过，学到很多东西的诀窍，就是一下子不要学很多。这启发了我. 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 现在，解决问题的问题，是非常非常重要的。 所以， 歌德说过一句著名的话，读一本好书，就如同和一个高尚的人在交谈。这似乎解答了我的疑惑. 对我个人而言，问题不仅仅是一个重大的事件，还可能会改变我的人生。 而这些并不是完全重要，更加重要的问题是， 问题，到底应该如何实现。 问题的关键究竟为何。\r\n　　本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。 ', '2021-09-05 14:12:42', '2021-09-18 16:02:11', 0, 0, 0, 0);
INSERT INTO `article` VALUES (6, 4, 1, 'test6', '一般来说， 要想清楚，问题，到底是一种怎么样的存在。 问题，发生了会如何，不发生又会如何。 要想清楚，问题，到底是一种怎么样的存在。 我们不得不面对一个非常尴尬的事实，那就是， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 问题因何而发生？ 从这个角度来看， 了解清楚问题到底是一种怎么样的存在，是解决一切问题的关键。 既然如何， 带着这些问题，我们来审视一下问题。 每个人都不得不面对这些问题。 在面对这种问题时， 经过上述讨论， 洛克曾经说过，学到很多东西的诀窍，就是一下子不要学很多。这启发了我. 本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 现在，解决问题的问题，是非常非常重要的。 所以， 歌德说过一句著名的话，读一本好书，就如同和一个高尚的人在交谈。这似乎解答了我的疑惑. 对我个人而言，问题不仅仅是一个重大的事件，还可能会改变我的人生。 而这些并不是完全重要，更加重要的问题是， 问题，到底应该如何实现。 问题的关键究竟为何。\r\n　　本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。 ', '2021-09-05 14:12:52', '2021-09-18 16:02:17', 52, 0, 0, 0);

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type`  (
  `article_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_node` int(11) NOT NULL,
  PRIMARY KEY (`article_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES (1, '个人项目', -1);
INSERT INTO `article_type` VALUES (2, '程序人生', -1);
INSERT INTO `article_type` VALUES (3, '安卓相关', 1);
INSERT INTO `article_type` VALUES (4, '博客系统', 1);
INSERT INTO `article_type` VALUES (5, '网站相关', 1);
INSERT INTO `article_type` VALUES (6, '小程序', 1);
INSERT INTO `article_type` VALUES (7, 'PHP', 2);
INSERT INTO `article_type` VALUES (8, 'web前端', 2);
INSERT INTO `article_type` VALUES (9, 'Python', 2);
INSERT INTO `article_type` VALUES (10, '算法', 2);
INSERT INTO `article_type` VALUES (11, 'Java', 2);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `floor` int(11) NOT NULL,
  `comment_uid` int(11) NOT NULL,
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `comment_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `comment_praise_number` int(11) NOT NULL DEFAULT 0,
  `delete_tag` int(1) NOT NULL DEFAULT 0,
  `top_tag` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 1, 1, '1', '2021-09-05 14:14:44', 0, 0, 0);
INSERT INTO `comment` VALUES (2, 1, 2, 1, '2', '2021-09-05 14:15:09', 0, 0, 0);
INSERT INTO `comment` VALUES (3, 1, 3, 1, '3', '2021-09-05 14:15:24', 0, 0, 0);
INSERT INTO `comment` VALUES (4, 1, 4, 1, '4', '2021-09-05 14:15:28', 0, 0, 0);
INSERT INTO `comment` VALUES (5, 1, 5, 1, '5', '2021-09-05 14:15:31', 0, 0, 0);
INSERT INTO `comment` VALUES (6, 1, 6, 1, '6', '2021-09-05 14:15:35', 0, 0, 0);
INSERT INTO `comment` VALUES (7, 1, 7, 1, '7', '2021-09-05 14:15:39', 0, 0, 0);
INSERT INTO `comment` VALUES (8, 1, 8, 1, '8', '2021-09-05 14:15:47', 0, 0, 0);
INSERT INTO `comment` VALUES (9, 1, 9, 1, '9', '2021-09-05 14:15:51', 0, 0, 0);
INSERT INTO `comment` VALUES (10, 1, 10, 1, '10', '2021-09-05 14:15:55', 0, 0, 0);
INSERT INTO `comment` VALUES (11, 1, 11, 1, '11', '2021-09-05 14:15:59', 0, 0, 0);
INSERT INTO `comment` VALUES (12, 1, 12, 1, '<p>欢迎留言！</p>', '2021-09-11 20:20:42', 0, 0, 0);
INSERT INTO `comment` VALUES (13, 1, 13, 1, '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。', '2021-09-11 20:21:37', 0, 0, 0);
INSERT INTO `comment` VALUES (14, 1, 14, 1, '本人也是经过了深思熟虑，在每个日日夜夜思考这个问题。 白哲特曾经提到过，坚强的信念能赢得强者的心，并使他们变得更坚强。 这句话看似简单，但其中的阴郁不禁让人深思。', '2021-09-11 20:21:50', 0, 0, 0);
INSERT INTO `comment` VALUES (15, 1, 15, 1, '<p>欢迎留言！</p>', '2021-09-11 20:24:36', 0, 0, 0);
INSERT INTO `comment` VALUES (16, 1, 16, 1, '<p>欢迎留言！&lt;a href=\"javascript:alert(\'xss\');\"&gt;点击&lt;/a&gt;</p>', '2021-09-11 20:26:52', 0, 0, 0);
INSERT INTO `comment` VALUES (18, 1, 18, 1, '<p><br></p><pre lang=\"Javascript\" theme=\"light\"><div class=\"tb-code-line-number-bg\"></div><div class=\"tb-code-content\"><div class=\"tb-code-line\"><code><span class=\"tb-hl-keyword\">let</span>&nbsp;x =&nbsp;<span class=\"tb-hl-number\">10</span></code></div><div class=\"tb-code-line\"><code><span class=\"tb-hl-keyword\">function</span>&nbsp;<span class=\"tb-hl-function\">juge</span>() {</code></div><div class=\"tb-code-line\"><code>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"tb-hl-keyword\">return</span>&nbsp;<span class=\"tb-hl-boolean\">true</span></code></div><div class=\"tb-code-line\"><code>&nbsp;&nbsp;}</code></div></div><div class=\"tb-pre-lang\">Javascript</div></pre><p style=\"text-indent:2em\">测试缩进</p><tb-todo-list><div class=\"tb-todo-list-item\"><div class=\"tb-todo-list-btn\"><div class=\"tb-todo-list-state tb-todo-list-state-active\"></div></div><div class=\"tb-todo-list-content\">似乎正常...</div></div></tb-todo-list><tb-word-explain><div style=\"width:140px\" class=\"tb-word-explain-title-group\"><div style=\"text-align:right\" class=\"tb-word-explain-title\">名词</div><div style=\"text-align:right\" class=\"tb-word-explain-subtitle\">组件</div></div><div class=\"tb-word-explain-detail\">没有问题</div></tb-word-explain><p><br></p>', '2021-09-11 21:18:43', 0, 0, 0);
INSERT INTO `comment` VALUES (19, 1, 19, 1, '<p><br></p><pre lang=\"Javascript\" theme=\"light\"><div class=\"tb-code-line-number-bg\"></div><div class=\"tb-code-content\"><div class=\"tb-code-line\"><code>代码</code></div></div><div class=\"tb-pre-lang\">Javascript</div></pre><p><br></p>', '2021-09-11 21:32:46', 0, 0, 0);
INSERT INTO `comment` VALUES (20, 1, 20, 1, '<p><br></p><pre lang=\"Javascript\" theme=\"light\"><div class=\"tb-code-line-number-bg\"></div><div class=\"tb-code-content\"><div class=\"tb-code-line\"><code>代码</code></div></div><div class=\"tb-pre-lang\">Javascript</div></pre><p><br></p>', '2021-09-11 21:35:41', 0, 0, 0);
INSERT INTO `comment` VALUES (21, 1, 21, 1, '<p><br></p><pre lang=\"Javascript\" theme=\"light\"><div class=\"tb-code-line-number-bg\"></div><div class=\"tb-code-content\"><div class=\"tb-code-line\"><code><span class=\"tb-hl-number\">24545</span></code></div></div><div class=\"tb-pre-lang\">Javascript</div></pre><p><br></p>', '2021-09-11 21:39:39', 0, 0, 0);
INSERT INTO `comment` VALUES (22, 1, 22, 1, '<p><br></p><pre lang=\"Javascript\" theme=\"light\"><div class=\"tb-code-line-number-bg\"></div><div class=\"tb-code-content\"><div class=\"tb-code-line\"><code><span class=\"tb-hl-keyword\">let</span>&nbsp;x =&nbsp;<span class=\"tb-hl-number\">0</span></code></div></div><div class=\"tb-pre-lang\">Javascript</div></pre><p><br></p>', '2021-09-11 21:41:41', 0, 0, 0);
INSERT INTO `comment` VALUES (23, 1, 23, 1, '<p><img src=\"https:&#x2F;&#x2F;textbus.tanboui.com&#x2F;tbus-bg.jpg\" style=\"width:521px;height:293px\">图片能用吗<br></p>', '2021-09-12 09:47:28', 0, 0, 0);

-- ----------------------------
-- Table structure for comment_tree
-- ----------------------------
DROP TABLE IF EXISTS `comment_tree`;
CREATE TABLE `comment_tree`  (
  `comment_tree_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `comment_uid` int(11) NOT NULL,
  `to_comment_uid` int(11) NOT NULL,
  `comment_tree_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`comment_tree_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_tree
-- ----------------------------

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mail
-- ----------------------------
INSERT INTO `mail` VALUES (1, '<!DOCTYPE html>\r\n<html>\r\n        <head>\r\n                <meta charset=\"utf-8\">\r\n                <title></title>\r\n        </head>\r\n        <body>\r\n                <div>\r\n                        <div>\r\n                                <includetail>\r\n                                        <div align=\"center\">\r\n                                                <div class=\"open_email\"\r\n                                                        style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\">\r\n                                                        <div>\r\n                                                                <br>\r\n                                                                <span class=\"genEmailContent\">\r\n                                                                        <div id=\"cTMail-Wrap\"\r\n                                                                                style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:\'helvetica neue\',PingFangSC-Light,arial,\'hiragino sans gb\',\'microsoft yahei ui\',\'microsoft yahei\',simsun,sans-serif\">\r\n                                                                                <div class=\"main-content\">\r\n                                                                                        <table\r\n                                                                                                style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\r\n                                                                                                <tbody>\r\n                                                                                                        <tr style=\"font-weight:300\">\r\n                                                                                                                <td style=\"width:3%;max-width:30px;\"></td>\r\n                                                                                                                <td style=\"max-width:600px;\">\r\n                                                                                                                        <!-- {{--   页面上边的蓝色分割线  --}} -->\r\n                                                                                                                        <p\r\n                                                                                                                                style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\">\r\n                                                                                                                        </p>\r\n\r\n                                                                                                                        <div id=\"cTMail-inner\"\r\n                                                                                                                                style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\">\r\n                                                                                                                                <table\r\n                                                                                                                                        style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\">\r\n                                                                                                                                        <tbody>\r\n                                                                                                                                                <!-- {{--   第一个单元格  --}} -->\r\n                                                                                                                                                <tr style=\"font-weight:300\">\r\n                                                                                                                                                        <!-- {{--   左侧表格，设置左边距用的  --}} -->\r\n                                                                                                                                                        <td style=\"width:3.2%;max-width:30px;\"></td>\r\n                                                                                                                                                        <!-- {{--   中间表格，正文使用  --}} -->\r\n                                                                                                                                                        <td\r\n\r\nstyle=\"max-width:480px;text-align:left;\">\r\n\r\n<!-- {{--   以下是正文 --}}\r\n                                                                                    {{--   可以是标题  --}} -->\r\n\r\n<h1 id=\"cTMail-title\"\r\n\r\n        style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">\r\n\r\n        【satoru网】欢迎注册satoru网账号\r\n\r\n</h1>\r\n\r\n\r\n<p id=\"cTMail-userName\"\r\n\r\n        style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">\r\n\r\n        亲爱的{username}用户，您好！\r\n\r\n</p>\r\n\r\n\r\n<p class=\"cTMail-content\"\r\n\r\n        style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\r\n\r\n        <span\r\n\r\n                style=\"color: rgb(51, 51, 51); font-size: 14px;\">\r\n\r\n                欢迎注册satoru网。\r\n\r\n        </span>\r\n\r\n</p>\r\n\r\n\r\n<p class=\"cTMail-content\"\r\n\r\n        style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\r\n\r\n        <span\r\n\r\n                style=\"color: rgb(51, 51, 51); font-size: 14px;\">完成注册，请点击下面按钮验证邮箱。\r\n\r\n                <span style=\"font-weight: bold;\">非本人操作可忽略。</span>\r\n\r\n        </span>\r\n\r\n</p>\r\n\r\n\r\n<!-- {{--   按钮  --}} -->\r\n\r\n<p class=\"cTMail-content\"\r\n\r\n        style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\">\r\n\r\n        <!-- {{--   下面替换成自己的链接  --}} -->\r\n\r\n        <a id=\"cTMail-btn\" href=\"{website}\" title=\"\"\r\n\r\n                style=\"font-size: 16px; line-height: 45px; display: block; background-color: rgb(0, 164, 255); color: rgb(255, 255, 255); text-align: center; text-decoration: none; margin-top: 20px; border-radius: 3px;\">\r\n\r\n                点击此处验证邮箱\r\n\r\n        </a>\r\n\r\n</p>\r\n\r\n\r\n<p class=\"cTMail-content\"\r\n\r\n        style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\r\n\r\n        <span\r\n\r\n                style=\"color: rgb(51, 51, 51); font-size: 14px;\">\r\n\r\n                <br>\r\n\r\n                无法正常显示？请复制以下链接至浏览器打开：\r\n\r\n                <br>\r\n\r\n                <a href=\"{website}\" title=\"\"\r\n\r\n                        style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">\r\n\r\n                        {website}\r\n\r\n                </a>\r\n\r\n        </span>\r\n\r\n</p>\r\n\r\n<p class=\"cTMail-content\">\r\n\r\n        <span style=\"font-weight: bold;\">※此邮件由系统自动发送，请不要直接回复。</span>\r\n\r\n</p>\r\n                                                                                                                                                        </td>\r\n                                                                                                                                                        <td style=\"width:3.2%;max-width:30px;\"></td>\r\n                                                                                                                                                </tr>\r\n                                                                                                                                        </tbody>\r\n                                                                                                                                </table>\r\n                                                                                                                        </div>\r\n\r\n                                                                                                                        </td>\r\n                                                                                                                <td style=\"width:3%;max-width:30px;\"></td>\r\n                                                                                                        </tr>\r\n                                                                                                </tbody>\r\n                                                                                        </table>\r\n                                                                                </div>\r\n                                                                        </div>\r\n                                                                </span>\r\n                                                        </div>\r\n                                                </div>\r\n                                        </div>\r\n                                </includetail>\r\n                        </div>\r\n\r\n                </div>\r\n        </body>\r\n</html>');
INSERT INTO `mail` VALUES (2, '<!DOCTYPE html>\r\n<html>\r\n        <head>\r\n                <meta charset=\"utf-8\">\r\n                <title></title>\r\n        </head>\r\n        <body>\r\n                <div>\r\n                        <div>\r\n                                <includetail>\r\n                                        <div align=\"center\">\r\n                                                <div class=\"open_email\"\r\n                                                        style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\">\r\n                                                        <div>\r\n                                                                <br>\r\n                                                                <span class=\"genEmailContent\">\r\n                                                                        <div id=\"cTMail-Wrap\"\r\n                                                                                style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:\'helvetica neue\',PingFangSC-Light,arial,\'hiragino sans gb\',\'microsoft yahei ui\',\'microsoft yahei\',simsun,sans-serif\">\r\n                                                                                <div class=\"main-content\">\r\n                                                                                        <table\r\n                                                                                                style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\r\n                                                                                                <tbody>\r\n                                                                                                        <tr style=\"font-weight:300\">\r\n                                                                                                                <td style=\"width:3%;max-width:30px;\"></td>\r\n                                                                                                                <td style=\"max-width:600px;\">\r\n                                                                                                                        <!-- {{--   页面上边的蓝色分割线  --}} -->\r\n                                                                                                                        <p\r\n                                                                                                                                style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\">\r\n                                                                                                                        </p>\r\n\r\n                                                                                                                        <div id=\"cTMail-inner\"\r\n                                                                                                                                style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\">\r\n                                                                                                                                <table\r\n                                                                                                                                        style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\">\r\n                                                                                                                                        <tbody>\r\n                                                                                                                                                <!-- {{--   第一个单元格  --}} -->\r\n                                                                                                                                                <tr style=\"font-weight:300\">\r\n                                                                                                                                                        <!-- {{--   左侧表格，设置左边距用的  --}} -->\r\n                                                                                                                                                        <td style=\"width:3.2%;max-width:30px;\"></td>\r\n                                                                                                                                                        <!-- {{--   中间表格，正文使用  --}} -->\r\n                                                                                                                                                        <td\r\n\r\nstyle=\"max-width:480px;text-align:left;\">\r\n\r\n<!-- {{--   以下是正文 --}}\r\n                                                                                    {{--   可以是标题  --}} -->\r\n\r\n<h1 id=\"cTMail-title\"\r\n\r\n        style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">\r\n\r\n        【satoru网】satoru网账号密码重置\r\n\r\n</h1>\r\n\r\n\r\n<p id=\"cTMail-userName\"\r\n\r\n        style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">\r\n\r\n        亲爱的{username}用户，请点击下方按钮进行密码重置！\r\n\r\n</p>\r\n\r\n\r\n<!-- {{--   按钮  --}} -->\r\n\r\n<p class=\"cTMail-content\"\r\n\r\n        style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\">\r\n\r\n        <!-- {{--   下面替换成自己的链接  --}} -->\r\n\r\n        <a id=\"cTMail-btn\" href=\"{website}\" title=\"\"\r\n\r\n                style=\"font-size: 16px; line-height: 45px; display: block; background-color: rgb(0, 164, 255); color: rgb(255, 255, 255); text-align: center; text-decoration: none; margin-top: 20px; border-radius: 3px;\">\r\n\r\n                点击此处重置密码\r\n\r\n        </a>\r\n\r\n</p>\r\n\r\n\r\n<p class=\"cTMail-content\"\r\n\r\n        style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\r\n\r\n        <span\r\n\r\n                style=\"color: rgb(51, 51, 51); font-size: 14px;\">\r\n\r\n                <br>\r\n\r\n                无法正常显示？请复制以下链接至浏览器打开：\r\n\r\n                <br>\r\n\r\n                <a href=\"{website}\" title=\"\"\r\n\r\n                        style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">\r\n\r\n                        {website}\r\n\r\n                </a>\r\n\r\n        </span>\r\n\r\n</p>\r\n\r\n<p class=\"cTMail-content\">\r\n\r\n        <span style=\"font-weight: bold;\">※此邮件由系统自动发送，请不要直接回复。</span>\r\n\r\n</p>\r\n                                                                                                                                                        </td>\r\n                                                                                                                                                        <td style=\"width:3.2%;max-width:30px;\"></td>\r\n                                                                                                                                                </tr>\r\n                                                                                                                                        </tbody>\r\n                                                                                                                                </table>\r\n                                                                                                                        </div>\r\n                                                                                                                        </td>\r\n                                                                                                                <td style=\"width:3%;max-width:30px;\"></td>\r\n                                                                                                        </tr>\r\n                                                                                                </tbody>\r\n                                                                                        </table>\r\n                                                                                </div>\r\n                                                                        </div>\r\n                                                                </span>\r\n                                                        </div>\r\n                                                </div>\r\n                                        </div>\r\n                                </includetail>\r\n                        </div>\r\n                </div>\r\n        </body>\r\n</html>');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/api/img/download/default.jpg',
  `role` enum('admin','normal','guest') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'normal',
  `register_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email_verify` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '0c56ae2b2ae1873d216f12dc41cc0171', '管理员', '123@qq.com', '/api/img/download//1631929046612.jpg', 'admin', '2021-09-16 10:34:29', 1);
INSERT INTO `user` VALUES (3, 'a', '0c56ae2b2ae1873d216f12dc41cc0171', '11111', '6@qq.com', '/api/img/download/default.jpg', 'normal', '2021-09-16 10:34:30', 1);
INSERT INTO `user` VALUES (4, '11111', '0c56ae2b2ae1873d216f12dc41cc0171', '1', '11@qq.com', '/api/img/download/default.jpg', 'normal', '2021-09-16 10:34:32', 0);
INSERT INTO `user` VALUES (5, 'guest', '0c56ae2b2ae1873d216f12dc41cc0171', '游客', '1', '/api/img/download/default.jpg', 'normal', '2021-09-16 14:22:04', 1);
INSERT INTO `user` VALUES (8, '1111', '0c56ae2b2ae1873d216f12dc41cc0171', '1111', '21', '/api/img/download/default.jpg', 'normal', '2021-09-16 16:12:46', 0);
INSERT INTO `user` VALUES (9, '2', '0c56ae2b2ae1873d216f12dc41cc0171', '2', '22', '/api/img/download/default.jpg', 'normal', '2021-09-16 16:13:49', 0);
INSERT INTO `user` VALUES (10, '3', '0c56ae2b2ae1873d216f12dc41cc0171', '3', '3', '/api/img/download/default.jpg', 'normal', '2021-09-16 16:14:02', 0);
INSERT INTO `user` VALUES (11, '4', '0c56ae2b2ae1873d216f12dc41cc0171', '4', '4', '/api/img/download/default.jpg', 'normal', '2021-09-16 16:14:12', 0);
INSERT INTO `user` VALUES (12, '1', '0c56ae2b2ae1873d216f12dc41cc0171', 'test2', '111', '/api/img/download/default.jpg', 'normal', '2021-09-17 10:28:08', 0);
INSERT INTO `user` VALUES (16, '45648', '0c56ae2b2ae1873d216f12dc41cc0171', 'asd', '57@qq.com', '/api/img/download/default.jpg', 'normal', '2021-09-18 14:21:21', 1);

SET FOREIGN_KEY_CHECKS = 1;
