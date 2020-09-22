package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.*;
import java.util.Random;

public class FavoritesTest extends BaseTest{

    @Test
    void nameOfAddedItemToFavoriteIsCorrectFromCategoryPage(){
        CategoryPage categoryPage = new CategoryPage(driver,wait)
                .goTo(configuration.getBaseUrl() + testData.getCategoryURL());
        System.out.println("weszlo");
        String productName = categoryPage.addItemToFavorites().getProductName();
        String addedItem = categoryPage.header.viewFavorites().getProductName();
        Assert.assertEquals(addedItem, productName);
    }

    @Test
    void nameOfAddedItemToFavoriteIsCorrectFromProductPage() {
        ProductPage productPage = new ProductPage(driver, wait)
                .goTo(configuration.getBaseUrl() + testData.getProductURL());
        productPage.closeCookiePopup();
        String productName = productPage.addItemToFavorites().getProductName();
        String addedItem = productPage.header.viewFavorites().getProductName();
        Assert.assertEquals(addedItem, productName);
    }

    @Test
    void shouldBeRemovedFromFavoritesWhenUnclickButtonOnProductPage(){
        ProductPage productPage = new ProductPage(driver, wait)
                .goTo(configuration.getBaseUrl() + testData.getProductURL());
        productPage.closeCookiePopup();
        productPage.addItemToFavorites().addItemToFavorites();

        Assert.assertFalse(productPage.isFavoriteButtonActive());
    }

    @Test
    void shouldBeRemovedFromFavoritesWhenUnclickButtonOnCategoryPage(){
        CategoryPage categoryPage = new CategoryPage(driver,wait)
                .goTo(configuration.getBaseUrl() + testData.getCategoryURL()).addItemToFavorites()
                .removeFromFavorites();
        categoryPage.header.viewFavorites();
        FavoritesPage favoritesPage= new FavoritesPage(driver,wait);
        Assert.assertTrue(favoritesPage.isFavoriteListEmpty());
    }

    @Test
    void shouldFavoritesIconBeInactiveOnProductPageWhenItemRemovedFromFavoritesPage(){
        ProductPage productPage = new ProductPage(driver, wait)
                .goTo(configuration.getBaseUrl() + testData.getProductURL());
        productPage.closeCookiePopup();
        productPage.addItemToFavorites().header.viewFavorites().removeItemFromFavorites();
        productPage.goTo(testData.getProductURL());
        Assert.assertFalse(productPage.isFavoriteButtonActive());
    }


    @Test
    void isPossibleToAddMultipleItemsToFavorites(){
        Random rand = new Random();
        int itemQty = rand.nextInt(15);
        CategoryPage categoryPage = new CategoryPage(driver,wait)
                .goTo(configuration.getBaseUrl() + testData.getCategoryURL());
        String itemsQty = categoryPage.addItemsToFavorites(itemQty).header.viewFavorites().getItemsQuantity();
        Assert.assertEquals(itemsQty, itemQty + " Items");
    }

    @Test
    void shouldURLbeCorrectWhenUserGoToFavorites(){
        FavoritesPage favoritesPage = new FavoritesPage(driver,wait)
                .goTo(configuration.getBaseUrl() + testData.getCategoryURL()).header.viewFavorites();
        Assert.assertTrue(favoritesPage.URLisCorrect("favourites"));
    }

    @Test
    void refreshingPageDoesntAffectFavoritesList(){
        CategoryPage categoryPage = new CategoryPage(driver,wait)
                .goTo(configuration.getBaseUrl() + testData.getCategoryURL()).addItemsToFavorites(2);
        FavoritesPage favoritesPage = new FavoritesPage(driver,wait).header.viewFavorites();
        favoritesPage.refreshPage();
        String itemsQty = favoritesPage.getItemsQuantity();
        Assert.assertEquals(itemsQty, "2 Items");
    }

}
