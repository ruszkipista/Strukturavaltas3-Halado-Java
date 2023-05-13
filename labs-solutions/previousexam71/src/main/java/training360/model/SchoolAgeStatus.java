package training360.model;

public enum SchoolAgeStatus {

    SCHOOL_AGED, NOT_SCHOOL_AGED;

    public static SchoolAgeStatus getStatus(long ageInYears) {
        return ageInYears > 16 ? NOT_SCHOOL_AGED : SCHOOL_AGED;
    }

}
