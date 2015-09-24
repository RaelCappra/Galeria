/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
//TODO (IMPORTANTE): Refatorar o banco e os daos para nao usar hard-delete(usar boolean inativo inves disso)
import java.util.List;

/**
 *
 * @author Aluno
 * @param <T>
 * @param <PK>
 */
public interface Dao<T, PK> {
    
    static final String DATABASE = "galeria";
    
    void save(T entity);

    void delete(PK id);

    List<T> listAll();
    
    T getById(PK pk);

}
