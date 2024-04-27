package dev.muskrat.aquatic.example.steps;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selenide;
import dev.muskrat.aquatic.example.contexts.GoogleTestContext;
import dev.muskrat.aquatic.example.service.SomeService;
import dev.muskrat.aquatic.example.service.SomeServiceImpl;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStep;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStepExecution;
import dev.muskrat.aquatic.lib.common.annotations.AquaticStepParam;
import dev.muskrat.aquatic.spring.Aquatic;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;


@Aquatic
@AquaticStep(
        id = "WRITE_TO_SEARCH_GOOGLE_STEP",
        name = "Набор текста в поисковое окно гугла",
        preCondition = "Opened google",
        postCondition = "Open google and in search line printed 'java'"
)
@NoArgsConstructor
@AllArgsConstructor
public class WriteToSearchGoogleStep {

    @Autowired
    SomeService someService;

    @Builder
    public static class Params {

        @Builder.Default
        @AquaticStepParam
        private String searchText = "java";
    }

    @AquaticStepExecution
    public void execution(GoogleTestContext context, Params params) {
        WebElement element = $x("//textarea");
        element.sendKeys(params.searchText);
        context.setSearch(params.searchText);


        //someService.call();
    }

    public static Params searchCake() {
        return Params.builder()
                .searchText("cake")
                .build();
    };

    public static Params searchDonat() {
        return Params.builder()
                .searchText("donat")
                .build();
    };
}
