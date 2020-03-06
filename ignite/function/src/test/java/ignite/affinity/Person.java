package ignite.affinity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

/**
 * Created by Administrator on 2019/12/25.
 */
public class Person {
    @QuerySqlField(index = true)
    private String personId ;

    @QuerySqlField(index = true)
    private String companyId ;

    @QuerySqlField
    private String personName ;

    public Person() {
    }

    public Person(String personId, String companyId, String personName) {
        this.personId = personId;
        this.companyId = companyId;
        this.personName = personName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
