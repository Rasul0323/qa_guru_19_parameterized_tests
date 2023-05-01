package kancmir_tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;


public class KancMirParamTests extends TestBase {

    @BeforeEach
    void setUp() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://kanc-mir.ru/";
    }

    @CsvSource(value = {
            "О магазине, О магазине",
            "Оплата, Оплата",
            "Доставка, Доставка"
    })
    @ParameterizedTest(name = "тестирование раздела {0}")
    void officeHeadButtonTest(String razdel, String result) {

        Selenide.open("");
        Selenide.executeJavaScript("$('div.tippy-content').remove()");

        $$x("//ul//li//a").findBy(Condition.text(razdel)).click();
        $("h1").shouldHave(Condition.text(result));
    }
    static Stream<Arguments> sideMenuTest() {
        return Stream.of(
                Arguments.of(List.of("Доставка", "Оплата", "Как сделать заказ", "Гарантии", "Возврат", "Вопросы и ответы", "Статьи"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "тестирование отображения разделов в боковом меню")
    void sideMenuTest(List<String> result) {

        Selenide.open("info/payment/");
        Selenide.executeJavaScript("$('div.tippy-content').remove()");

        $$x("//ul[@class = 'left_menu']/li/a")
                .filter(Condition.visible).shouldHave(CollectionCondition.containExactTextsCaseSensitive(result));

    }

    @ValueSource(strings = {
            "Бренды",
            "О магазине",
            "Оплата",
            "Доставка",
            "Контакты",
            "Реквизиты",
            "Вакансии"
    }
    )
    @ParameterizedTest(name = "тестирование кнопки {0} на странице канцмир")
    void promotionTest(String razdel) {

        Selenide.open("https://kanc-mir.ru/");
        Selenide.executeJavaScript("$('div.tippy-content').remove()");

        $$x("//ul[@class = 'menu topest']/li/a").findBy(Condition.text(razdel)).click();
        $x("//ul[@class = 'menu topest']/li/a ']/a[@class = 'current']")
                .shouldHave(Condition.exactText(razdel));

    }
}

