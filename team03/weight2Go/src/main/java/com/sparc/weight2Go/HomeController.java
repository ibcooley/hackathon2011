package com.sparc.weight2Go;

import java.text.DateFormat;
import java.util.*;

import com.sparc.weight2GoJPA.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserDao userDao;

    @Autowired
    private UserFoodDao userFoodDao;

    @Autowired
    private FoodDao foodDao;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, Model model) {
        User user = createUser();
        session.setAttribute("user", user);
        Collection<UserFood> userFoods = user.getUserFoods();
        model.addAttribute("userFoods", userFoods);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);
        return "foodList";
    }

    @RequestMapping(value = "/userEntry", method = RequestMethod.GET)
    public String userEntry() {
        return "userEntry";
    }

    @RequestMapping(value = "/savePersonalInformation", method = RequestMethod.POST)
    public String savePersonalInformation(@ModelAttribute User user, Model model) {


/*        User user = new User();
        user.setFirstName("testFirst");
        user.setLastName("testLast");
        user.setAge(24);
        user.setStartWeight(200);
        user.setTargetWeight(170);*/

        userDao.persist(user);
        List<User> users = userDao.getAllUsers();

        return "foodEntry";
    }

    @RequestMapping(value = "/editFoodEntry", method = RequestMethod.GET)
    public String editFoodEntry(@RequestParam Long userFoodId, Model model) {
        UserFood userFood = userFoodDao.getUserFoodById(userFoodId);
        model.addAttribute("userFood", userFood);

        return "foodEntry";
    }

    @RequestMapping(value = "/saveFoodEntry", method = RequestMethod.POST)
    public String saveFoodEntry(UserFood sourceUserFood, HttpSession session, Model model) {
        UserFood userFood = userFoodDao.getUserFoodById(sourceUserFood.getId());
        userFood.setMealType(sourceUserFood.getMealType());
        userFood.setAmount(sourceUserFood.getAmount());
        userFood.getFood().setCaloriesPerServing(sourceUserFood.getFood().getCaloriesPerServing());
        userFood.getFood().setCarbs(sourceUserFood.getFood().getCarbs());
        userFood.getFood().setFat(sourceUserFood.getFood().getFat());
        userFood.getFood().setProtein(sourceUserFood.getFood().getProtein());

//        userFoodDao.update(userFood);

        return displayFoodList(session, model);
    }

    @RequestMapping(value = "/displayFoodList", method = RequestMethod.GET)
    public String displayFoodList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Collection<UserFood> userFoods = user.getUserFoods();
        model.addAttribute("userFoods", userFoods);
        return "foodList";
    }

    private User createUser() {
        User user = new User();
        createUser(user);

        user.getUserWeights().addAll(createUserWeights(3));
        user.getUserFoods().addAll(createUserFoods(5));

        this.userDao.persist(user);

        return user;
    }

    private List<UserFood> createUserFoods(int numFoods) {
        List<UserFood> userFoods = new ArrayList<UserFood>();
        for (int x = 0; x < numFoods; x++) {
            UserFood userFood = new UserFood();
            userFood.setAmount(1);
            userFood.setFood(createFood());
            userFood.setMealType("breakfast");

            userFoods.add(userFood);

        }

        return userFoods;
    }

    private Food createFood() {
        Food food = new Food();
        food.setName("Beer");
        food.setCaloriesPerServing(200);
        food.setServingSize(1);

        foodDao.persist(food);

        return food;
    }

    private List<UserWeight> createUserWeights(int numWeights) {
        List<UserWeight> userWeights = new ArrayList<UserWeight>();
        for (int x = 0; x < numWeights; x++) {
            UserWeight userWeight = new UserWeight();
            userWeight.setWeight(180);

            userWeights.add(userWeight);
        }

        return userWeights;
    }

    private void createUser(User user) {
        user.setFirstName("testFirst");
        user.setLastName("testLast");
//        user.setDateOfBirth(24);
        user.setStartWeight(200);
        user.setTargetWeight(170);
    }

}
