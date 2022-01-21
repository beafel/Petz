
package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConsultaProduto {
    String url;
    WebDriver driver;
    String pastaPrint = "evidencias/" + new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime()) + "/";

    public void tirarPrint(String nomePrint) throws IOException {
        // variavel foto do tipo arquivo que vai receber o resultado do tipo selenium da imagem atual
        File foto = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); // especializacao do Selenium tirar foto, fotografia com saida para um arquivo
        FileUtils.copyFile(foto, new File(pastaPrint + nomePrint + ".png")); // grava o print da tela no arquivo
    }

    @Before
    public void iniciar() {
        url = "https://www.petz.com.br";
        //System.setProperty("webdriver.chrome.driver", "drivers/chrome/chromedriver96.exe");
        System.setProperty("webdriver.edge.driver", "drivers/edge/msedgedriver.exe");
        //ChromeOptions CO = new ChromeOptions();
        //CO.addArguments("--disable-notifications");
        //driver = new ChromeDriver(CO);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
        System.out.println("Passo 1 - Preparou o setup");
    }

    @After
    public void finalizar() {
        driver.quit();
        System.out.println("Passo 19 - Fechou o browser");
    }

    @Dado("^que acesso o site da Petz$")
    public void que_acesso_o_site_da_Petz() throws IOException {
        driver.get(url);
        System.out.println("Passo 2 - Acessou o site da Petz");
        tirarPrint("Print 1 - Exibe Site da Petz");
    }

    @Quando("^procuro por \"([^\"]*)\" e pressiono Enter$")
    public void procuro_por_e_pressiono_Enter(String termo) throws IOException {
        driver.findElement(By.id("search")).sendKeys(termo + Keys.ENTER);
        //driver.findElement(By.id("search")).sendKeys(Keys.ENTER);
        System.out.println("Passo 3 - Procurou por " + termo);
        //tirarPrint("Print 2 - Exibe a busca por " + termo); 
    }

    @Entao("^exibe a lista de produtos relacionados a \"([^\"]*)\"$")
    public void exibe_a_lista_de_produtos_relacionados_a(String termo) throws IOException {
        assertEquals(termo + " - Produtos pet em promoção | Petz", driver.getTitle());
        //assertEquals("Pet Shop: Petz o maior pet shop do Brasil.", driver.getTitle());
        assertEquals("RESULTADOS PARA \"" + termo.toUpperCase() + "\"",
                driver.findElement(By.cssSelector("h1.h2Categoria.nomeCategoria")).getText());
        System.out.println("Passo 4 - Exibiu a lista de produtos relacionados com " + termo);
        tirarPrint("Print 3 - Exibe a lista de produtos encontrados");
    }

    @Quando("^seleciono o primeiro produto da lista$")
    public void seleciono_o_primeiro_produto_da_lista() throws IOException {
        driver.findElement(By.xpath("//h3[contains(text(),'Ração Royal Canin Maxi - Cães Adultos - 15kg')]")).click();
        System.out.println("Passo 5 - Selecionou o primeiro produto da lista");
        tirarPrint("Print 4 - Exibe produto selecionado");
    }

    @Entao("^verifico que a marca como \"([^\"]*)\" com preco normal de \"([^\"]*)\" e \"([^\"]*)\" para assinantes$")
    public void verifico_que_a_marca_como_com_preco_normal_de_e_para_assinantes(String marca, String precoNormal, String precoAssinante) throws Throwable {
        assertEquals(marca, driver.findElement(By.cssSelector("span.blue")).getText());
        assertEquals(precoNormal, driver.findElement(By.cssSelector("div.price-current")).getText());
        assertEquals(precoAssinante, driver.findElement(By.cssSelector("span.price-subscriber")).getText());
        System.out.println("Passo 6 - Verificou a marca como " + marca + ", o preco normal como " + precoNormal +
                " e o preco de assinante como " + precoAssinante);
    }

    @Quando("^procuro por \"([^\"]*)\" e clico na Lupa$")
    public void procuro_por_e_clico_na_Lupa(String termo) throws IOException {
        driver.findElement(By.id("search")).sendKeys(termo);
        driver.findElement(By.cssSelector("button.button-search")).click();
        System.out.println("Passo 7 - Procurou por " + termo);
        tirarPrint("Print 5 - Procura por " + termo + "clicando na lupa");
    }

    @Entao("^exibe uma lista de produtos aproximados e a mensagem de que nao encontrou \"([^\"]*)\"$")
    public void exibe_uma_lista_de_produtos_aproximados_e_a_mensagem_de_que_nao_encontrou(String termo) throws IOException {
        assertEquals("Os resultados desta busca são aproximados, pois não encontramos resultados para \""
                + termo + "\"", driver.findElement(By.cssSelector("span.descricao-lucene:nth-child(2)")).getText());
        System.out.println("Passo 8 - Procurou por " + termo);
        tirarPrint("Print 6 - Exibe produtos aproximados " + termo);
    }


    @Entao("^exibe uma caixa de alerta dizendo que precisa preencher pelo menos tres letras no termo$")
    public void exibeUmaCaixaDeAlertaDizendoQuePrecisaPreencherPeloMenosTresLetrasNoTermo() throws IOException {
        assertEquals("Digite pelo menos 3 caracteres", driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();
        tirarPrint("Print 7 - Exibe a busca por ab");
        System.out.println("Passo 9 - Procurou por ab");
    }
}