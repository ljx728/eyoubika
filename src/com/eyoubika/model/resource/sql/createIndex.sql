USE `eyoubika`;
CREATE INDEX tp_InvestArticle_ArticleId ON tp_InvestArticle (IA_ArticleId);

use eyoubika;
CREATE INDEX depositArticle_exId ON ta_DepositArticle (DA_ExId);
CREATE INDEX subscribeArticle_exId ON ta_SubscribeArticle (SA_ExId);