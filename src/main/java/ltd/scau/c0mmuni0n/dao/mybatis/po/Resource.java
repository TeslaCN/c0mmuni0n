package ltd.scau.c0mmuni0n.dao.mybatis.po;

public class Resource {
    private Long id;

    private String path;

    private String name;

    private String username;

    private Long uploadtime;

    private Long size;

    private Long type;

    private Byte disable;

    private Long clicked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Long uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Byte getDisable() {
        return disable;
    }

    public void setDisable(Byte disable) {
        this.disable = disable;
    }

    public Long getClicked() {
        return clicked;
    }

    public void setClicked(Long clicked) {
        this.clicked = clicked;
    }
}