package io.github.arthurdev.domain.reposiroty;

import java.util.List;

import io.github.arthurdev.domain.model.Follower;
import io.github.arthurdev.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower>{
    
    public boolean follows(User follower, User user){
        //faz um map dos atributos para serem passados por parâmetro para a query
        var parameters = Parameters.with("follower", follower).and("user", user).map();

        var query = find("follower =:follower and user =:user", parameters);
        var result = query.firstResultOptional();

        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId){
       var query = find("user.id", userId);
       return query.list();
    }

    public void deleteByFollowerAndUser(Long followerId, Long userId) {
        //criando um map com os parametros que vão ser usados na query
        var parameters = Parameters.with("userId", userId).and("followerId", followerId).map();

        delete("follower.id =:followerId and user.id =:userId", parameters);
    }
}
