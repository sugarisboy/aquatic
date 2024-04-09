package dev.muskrat.aquatic.example.steps;

import static com.codeborne.selenide.Selenide.$x;

import dev.muskrat.aquatic.example.contexts.GoogleTestContext;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStep;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStepExecution;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStepParam;
import dev.muskrat.aquatic.spring.Aquatic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;



@Aquatic
@AquaticStep(
        id = "WRITE_TO_SEARCH_GOOGLE_STEP",
        name = "Набор текста в поисковое окно гугла",
        preCondition = "Opened google",
        postCondition = "Open google and in search line printed 'java'"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteToSearchGoogleStep {

    @Builder.Default
    @AquaticStepParam
    private String searchText = "java";

    @AquaticStepExecution
    public void execution(GoogleTestContext context) {
        WebElement element = $x("//textarea");
        element.sendKeys(searchText);
        context.setSearch(searchText);
    }

    public static WriteToSearchGoogleStep searchCake() {
        return WriteToSearchGoogleStep.builder()
                .searchText("cake")
                .build();
    };

    public static WriteToSearchGoogleStep searchDonat() {
        return WriteToSearchGoogleStep.builder()
                .searchText("donat")
                .build();
    };
}
