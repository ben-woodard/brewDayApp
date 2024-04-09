package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.service.IngredientService;
import com.coderscampus.brewDayApp.service.ProductService;
import com.coderscampus.brewDayApp.service.RecipeService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final UserServiceImpl userService;
    private final ProductService productService;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;


    @Autowired
    public RecipeController(UserServiceImpl userService, ProductService productService, IngredientService ingredientService, RecipeService recipeService) {
        this.userService = userService;
        this.productService = productService;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/{userId}/home")
    public String getRecipesHome(ModelMap model, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("recipesList", recipeService.findAllRecipesByUser(user));
        return "recipe/home";
    }

    @PostMapping("/{productId}/create")
    public String createNewRecipeFromRecipeHome(@ModelAttribute Recipe recipe, @PathVariable Long productId) {
        Recipe savedRecipe = recipeService.createRecipeProductRelationship(recipe, productId);
        return "redirect:/recipes/{productId}/" + savedRecipe.getRecipeId();
    }

    @GetMapping("/{productId}/{recipeId}")
    public String getRecipeInformation(Model model, @PathVariable Long recipeId, @PathVariable Long productId) {
        Product product =  productService.findById(productId);
        Recipe recipe = recipeService.findById(recipeId);
        User user = product.getUser();
        model.addAttribute("recipeDTO", new RecipeDTO());
        model.addAttribute("ingredients", user.getIngredients());
        model.addAttribute("ingredientAmountMap", recipeService.getMapOfRecipeIngredientsAndAmounts(recipe));
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("recipe", recipe);
        return "recipe/create";
    }

//    @PostMapping("/{productId}/{recipeId}")
//    public String postUpdateRecipeInformation(@ModelAttribute Recipe recipe, )

    @PostMapping("/{recipeId}/addingredient")
    public String postAddIngredientToRecipe(@ModelAttribute RecipeDTO recipeDTO, @PathVariable Long recipeId) {
        Recipe recipe = recipeService.createRecipeIngredientRelationship(recipeDTO, recipeId);
        Product product = recipe.getProduct();
        return "redirect:/recipes/"+ product.getProductId() + "/" + recipe.getRecipeId();
    }

//
//    @GetMapping("/{productId}/create")
//    public String getCreateNewRecipe(ModelMap model, @PathVariable Long productId) {
//        Product product = productService.findById(productId);
//        Recipe recipe = new Recipe();
//        List<String> stringList = new ArrayList<>();
//        model.addAttribute("user", product.getUser());
//        model.addAttribute("ingredients", ingredientService.findAll());
//        model.addAttribute("product", product);
//        model.addAttribute("recipe", recipe);
//        model.addAttribute("toRemoveMap", recipe.getIngredientsToRemove());
//        model.addAttribute("stringList", stringList);
//        return "recipe/create";
//    }
//
//    @PostMapping("/{productId}/create")
//    public String postCreateNewRecipe(@ModelAttribute("recipeIngredients") List<Ingredient> recipeIngredients) {
//        recipeIngredients.stream().forEach(System.out::println);
//        return "redirect:/{productId}/create";
//    }
//
//    @ModelAttribute("recipeIngredients")
//    public List<Ingredient> getIngredients() {
//        return new ArrayList<>();
//    }

}
