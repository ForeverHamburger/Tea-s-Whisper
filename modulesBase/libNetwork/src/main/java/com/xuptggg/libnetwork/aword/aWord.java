package com.xuptggg.libnetwork.aword;


public class aWord {
    private int id;                  // ID
    private String uuid;             // UUID
    private String hitokoto;         // 内容
    private String type;             // 类型
    private String from;             // 出处
    private String fromWho;          // 作者（可能为 null）
    private String creator;          // 创建者
    private int creatorUid;         // 创建者 ID
    private int reviewer;           // 审核者 ID
    private String commitFrom;       // 提交来源
    private String createdAt;        // 创建时间（时间戳）
    private int length;              // 内容长度

    // 无参构造函数
    public aWord() {
    }

    // 带参构造函数
    public aWord(int id, String uuid, String hitokoto, String type, String from, String fromWho, String creator, int creatorUid, int reviewer, String commitFrom, String createdAt, int length) {
        this.id = id;
        this.uuid = uuid;
        this.hitokoto = hitokoto;
        this.type = type;
        this.from = from;
        this.fromWho = fromWho;
        this.creator = creator;
        this.creatorUid = creatorUid;
        this.reviewer = reviewer;
        this.commitFrom = commitFrom;
        this.createdAt = createdAt;
        this.length = length;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(int creatorUid) {
        this.creatorUid = creatorUid;
    }

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public String getCommitFrom() {
        return commitFrom;
    }

    public void setCommitFrom(String commitFrom) {
        this.commitFrom = commitFrom;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    public aWordMain getMain() {
        return new aWordMain(hitokoto, type, from, fromWho);
    }

    // toString 方法
    @Override
    public String toString() {
        return "aWord{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", hitokoto='" + hitokoto + '\'' +
                ", type='" + type + '\'' +
                ", from='" + from + '\'' +
                ", fromWho='" + fromWho + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorUid=" + creatorUid +
                ", reviewer=" + reviewer +
                ", commitFrom='" + commitFrom + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", length=" + length +
                '}';
    }

    public static class aWordMain {
        private String content;
        private String type;
        private String from;
        private String fromWho;
        public aWordMain(String content, String type, String from, String fromWho) {
            this.content = content;
            this.type = type;
            this.from = from;
            this.fromWho = fromWho;
        }

        @Override
        public String toString() {
            return "aWordMain{" +
                    "content='" + content + '\'' +
                    ", type='" + type + '\'' +
                    ", from='" + from + '\'' +
                    ", fromWho='" + fromWho + '\'' +
                    '}';
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFromWho() {
            return fromWho;
        }

        public void setFromWho(String fromWho) {
            this.fromWho = fromWho;
        }
    }
}