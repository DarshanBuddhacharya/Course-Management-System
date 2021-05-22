package Course.Management.System;

public class extract {
    private String Course_id, Name, Status = null;
    private String mod_id = null, name, course_id, Level, semester, ins_id, state;
    private String Ins_id, FullName = null;

    public extract(String mod_id, String name, String course_id, String Level, String semester, String ins_id, String state) {
        this.mod_id = mod_id;
        this.name = name;
        this.course_id = course_id;
        this.Level = Level;
        this.semester = semester;
        this.ins_id = ins_id;
        this.state = state;
    }

    public extract(String Course_id, String Name, String Status) {
        this.Course_id = Course_id;
        this.Name = Name;
        this.Status = Status;
    }

    public extract(String Ins_id, String FullName) {
        this.Ins_id = Ins_id;
        this.FullName = FullName;
    }
    public String getMod_id(){
        return mod_id;
    }
    public String getname(){
        return name;
    }
    public String getcourse_id(){
        return  course_id;
    }
    public String getLevel(){
        return Level;
    }
    public String getSemester(){
        return semester;
    }
    public String getins_id(){
        return ins_id;
    }
    public String getCourse_id(){
        return Course_id;
    }
    public String getName(){
        return Name;
    }
    public String getStatus(){
        return Status;
    }
    public String getIns_id(){
        return Ins_id;
    }
    public String getFullName(){
        return FullName;
    }
    public String getState(){return state;}
}
