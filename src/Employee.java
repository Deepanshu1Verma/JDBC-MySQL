public class Employee {
    private String name;
    private Integer phone;
    private Integer salary;
    private Integer id;

    // Constructor
    public Employee() {
    }

    public Employee(String name, Integer phone, Integer salary, Integer id) {
        this.name = name;
        this.phone = phone;
        this.salary = salary;
        this.id = id;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public Integer getPhone() {
        return phone;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getId() {
        return id;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
