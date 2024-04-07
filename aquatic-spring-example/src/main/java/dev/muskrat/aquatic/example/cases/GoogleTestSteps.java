package dev.muskrat.aquatic.example.cases;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selenide;
import dev.muskrat.aquatic.example.contexts.GoogleTestContext;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStep;
import dev.muskrat.aquatic.spring.Aquatic;
import lombok.RequiredArgsConstructor;

@Aquatic
@RequiredArgsConstructor
public class GoogleTestSteps {

    @AquaticStep(
            id = "OPEN_GOOGLE",
            name = "Открытие гугла",
            preCondition = "Opened browser",
            postCondition = "Opened google"
    )
    public void openGoogle(GoogleTestContext context) {
        Selenide.open("https://www.google.ru/");
    }

    @AquaticStep(
            id = "CLICK_SEARCH_BUTTON",
            name = "Search",
            preCondition = "Opened google and wrote search query",
            postCondition = "Search query successful finished"
    )
    public void search(GoogleTestContext context) {
        $x("//input[@role='button']").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
