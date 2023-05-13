package appointments.model;

public enum CaseType {
    C001("001", "Adóbevallás") , C002("002", "Befizetés");

    private String id;
    private String title;

    private CaseType(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public String getId(){
        return this.id;
    }
}
