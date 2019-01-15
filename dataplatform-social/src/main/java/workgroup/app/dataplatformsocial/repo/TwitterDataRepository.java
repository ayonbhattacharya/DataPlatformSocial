package workgroup.app.dataplatformsocial.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import workgroup.app.dataplatformsocial.datamodels.TwitterDataModel;

@Repository
public interface TwitterDataRepository extends MongoRepository<TwitterDataModel, String>{

}
