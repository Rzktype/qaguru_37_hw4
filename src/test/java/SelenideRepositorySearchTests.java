import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class SelenideRepositorySearchTests {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
        Configuration.timeout = 5000;
    }

    @Test
    void shouldFindSelenideRepository() {
        // открыть главную страницу
        open("https://github.com/");
        $("[data-target='qbsearch-input.inputButtonText']").click();//
        $("#query-builder-test").setValue("selenide").pressEnter();
        $("[data-testid='results-list']").$("a").click();
        $("#repository-container-header").shouldHave(text("selenide / selenide"));
        $("#wiki-tab").click();
        // проверяем наличие и открываем страницу Soft assertions
        $("#wiki-pages-box").$(withText("Show")).click();
        $("a[href$='SoftAssertions']").shouldBe(visible).click();
        $(".gh-header-title").shouldHave(text("SoftAssertions"));
        // найти примеры кода для JUnit5
        $(".repository-content").shouldHave(text("Using JUnit5 extend test class:"));
        $(".markdown-body").shouldHave(text(
                """
                        @ExtendWith({SoftAssertsExtension.class})
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                        
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }
                        """));
        $(".repository-content ").shouldHave(text("Or register extension inside test class:"));
        $(".markdown-body").shouldHave(text("""
                class Tests {
                  @RegisterExtension
                  static SoftAssertsExtension softAsserts = new SoftAssertsExtension();
                
                  @Test
                  void test() {
                    Configuration.assertionMode = SOFT;
                    open("page.html");
                
                    $("#first").should(visible).click();
                    $("#second").should(visible).click();
                  }
                }
                """));

    }

}
