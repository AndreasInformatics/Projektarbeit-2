package backend;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Depot")
public class Depot {
    @Transient
    private List<Investment> investmentlist = new ArrayList<>();
    public List<Investment> getInvestmentlist() {
        return investmentlist;
    }
    public void addtoDatabase(){
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AktienTrading");
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            em.persist(new Depot());
            et.commit();
        } catch (Exception ex){
            if(et != null){
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
        ENTITY_MANAGER_FACTORY.close();
    }

    public Depot(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int depot_id;

    public int getDepot_id() {
        return depot_id;
    }
    public void setDepot_id(int depot_id) {
        this.depot_id = depot_id;
    }
}
