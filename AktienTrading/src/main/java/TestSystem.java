import backend.CustomerAccount;
import javax.persistence.*;

public class TestSystem {

    public static void main(String[] args){
        addCustomer("Andreas", "Graubner", "abc@gmail.com", "abcstreet", "Berlin", "passwort", "5a", 1, 10551);
        //getCustomer(1);
    }
    //TODO Singleton pattern anschauen

    public static void addCustomer(String prename, String surname, String email, String street, String location, String password, String housenumber, int customernumber, int postcode){
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AktienTrading");
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            em.persist(new CustomerAccount(prename, surname, email, location, postcode, street, housenumber, password));
            CustomerAccount customerAccount = em.find(CustomerAccount.class, 1);
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
    /*
    public static void getCustomer(int customernumber){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM Customer c WHERE c.customernumber = :customernumber";

        TypedQuery<CustomerAccount> tq = em.createQuery(query, CustomerAccount.class);
        tq.setParameter("customernumber", customernumber);
        CustomerAccount customer = null;
        try {
            customer = tq.getSingleResult();
            System.out.println(customer.getPrename()+" "+customer.getSurname());
        } catch (NoResultException ex){
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }*/
}
