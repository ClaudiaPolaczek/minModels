package pl.polsl.polaczek.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class Test {

    @Autowired
    private Repository repo;

    @GetMapping
    public Tweet test()
    {
        //Tweet tweet = new Tweet();
        //tweet.message = "Hello";
        //tweet.id = "1";

        return repo.save(new Tweet(1L, "My message"));
    }

    @GetMapping("tweet")
    public List<Tweet> secondTest()
    {
        return repo.findAll();
    }

}
