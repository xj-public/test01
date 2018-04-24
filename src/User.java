/**
 * 用户
 *
 * @author xujian
 * @create 2018-04-23 14:30
 **/
public class User implements Comparable{
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        User u = (User) o;
        return u.getName().compareTo(this.getName());
    }
}
