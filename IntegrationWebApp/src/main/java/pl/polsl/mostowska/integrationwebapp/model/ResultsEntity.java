package pl.polsl.mostowska.integrationwebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Wiktoria
 */
@Entity
public class ResultsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double a;
    private double b;
    private double c;
    private double upperBound;
    private double lowerBound;
    private int partitions;
    private String result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
    public double getUpperBound() { return upperBound; }
    public double getLowerBound() { return lowerBound; }
    public int getPartitions() { return partitions; }
    public String getResult() { return result; }
    
    public void setA(double a) { this.a = a; }
    public void setB(double b) { this.b = b; }
    public void setC(double c) { this.c = c; }
    public void setUpperBound(double upperBound) { this.upperBound = upperBound; }
    public void setLowerBound(double lowerBound) { this.lowerBound = lowerBound; }
    public void setPartitions(int partitions) { this.partitions = partitions; }
    public void setResult(String result) { this.result = result; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultsEntity)) {
            return false;
        }
        ResultsEntity other = (ResultsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.polsl.mostowska.integrationwebapp.model.ResultsEntity[ id=" + id + " ]";
    }
    
    
}
