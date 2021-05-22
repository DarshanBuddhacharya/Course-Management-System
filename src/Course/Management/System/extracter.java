package Course.Management.System;

public class extracter {
    private String id, firstName, lastName, gender, address, email = null, course, level;
    private String mod_id,mod_name,marks,result;

    public extracter(String id, String firstName, String lastName, String gender, String address, String email, String course, String level){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.course = course;
        this.level =level;
        this.address = address;
    }

    public extracter(String mod_id, String mod_name,String marks, String result) {
        this.mod_id = mod_id;
        this.mod_name = mod_name;
        this.marks = marks;
        this.result = result;
    }


    public String getId(){
        return id;
    }
    public String getFN(){
        return firstName;
    }
    public String getLN(){
        return  lastName;
    }
    public String getGen(){
        return gender;
    }
    public String getEmail(){
        return email;
    }
    public String getCourse(){
        return course;
    }
    public String getlevel(){
        return level;
    }
    public String getAddress(){return address; }


    public String getMod_id(){return mod_id;}
    public String getMod_name(){return mod_name;}
    public String getMarks(){return marks;}
    public String getResult(){return result;}

}
