import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import groovy.json.JsonSlurper as JsonSlurper
import static org.assertj.core.api.Assertions.*

postResponse = WS.sendRequest(findTestObject('GoRest-Created Post', [('Title') : Title, ('Body') : Body, ('UserId') : UserId
            , ('Token') : Token]))

if (WS.verifyResponseStatusCode(postResponse, 201, FailureHandling.OPTIONAL)) {
    print('Postingan berhasil di buat')

    getPostUser = WS.sendRequest(findTestObject('GoRest-Get Post', [('UserId') : UserId]))

    def jsonSlurper = new JsonSlurper()

    def jsonResponse = jsonSlurper.parseText(getPostUser.getResponseText())

    assertThat(jsonResponse.title.toString()).contains(Title)

    assertThat(jsonResponse.body.toString()).contains(Body)
} else if (WS.verifyResponseStatusCode(postResponse, 422, FailureHandling.OPTIONAL)) {
    print('Ada inputan yang masih kosong')
} else if (WS.verifyResponseStatusCode(postResponse, 401, FailureHandling.OPTIONAL)) {
    print('Anda belum mendapatkan akses')
} else if (WS.verifyResponseStatusCode(postResponse, 404, FailureHandling.OPTIONAL)) {
    print('Service tidak tersedia')
}

