package com.practicas.conexiona;

import com.practicas.conexiona.model.Account;
import com.practicas.conexiona.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConexionaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class ConexionaApplicationTests {

    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreateAccount1() {
        Account account = new Account();
        account.setAccountId("1");
        account.setEmailAddress("user1@conexiona.com");
        account.setAccountName("account1");
        account.setAddress("Calle Corunha- Parque Tecnoloxico");
        account.setCompany("Conexiona");
        account.setDicomId("123");
        account.setEnabled(1);

        ResponseEntity<Account> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/accounts", account, Account.class);
        assertNotNull(postResponse1);
        assertNotNull(postResponse1.getBody());
    }

    @Test
    public void testCreateAccount2() {
        Account account = new Account();
        account.setAccountId("2");
        account.setEmailAddress("user1@conexiona.com");
        account.setAccountName("account2");
        account.setAddress("Calle Corunha- Parque Tecnoloxico");
        account.setCompany("Conexiona");
        account.setDicomId("123");
        account.setEnabled(1);

        ResponseEntity<Account> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/accounts", account, Account.class);
        assertNotNull(postResponse1);
        assertNotNull(postResponse1.getBody());
    }

    @Test
    public void testGetAllAccounts() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/accounts",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAccountById() {
        Account account = restTemplate.getForObject(getRootUrl() + "/accounts/1", Account.class);
        System.out.println(account.getAccountName());
        System.out.println(account.getCompany());
        System.out.println(account.getAddress());
        System.out.println(account.getEmailAddress());
        System.out.println(account.getDicomId());
        System.out.println(account.getEnabled());
        assertNotNull(account);
    }

    @Test
    public void testUpdateAccountPost() {
        int id = 1;
        Account account = restTemplate.getForObject(getRootUrl() + "/accounts/" + id, Account.class);
        account.setAccountName("cuenta1");
        account.setEmailAddress("hola_hola@conexiona.com");
        account.setCompany("Tecnopole");

        restTemplate.put(getRootUrl() + "/accounts/" + id, account);

        Account updatedAccount = restTemplate.getForObject(getRootUrl() + "/accounts/" + id, Account.class);
        assertNotNull(updatedAccount);
    }

    @Test
    public void testDeleteAccountPost() {
        int id = 2;
        Account account = restTemplate.getForObject(getRootUrl() + "/accounts/" + id, Account.class);
        assertNotNull(account);

        restTemplate.delete(getRootUrl() + "/accounts/" + id);

        try {
            account = restTemplate.getForObject(getRootUrl() + "/accounts/" + id, Account.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void testCreateUser1() {
        Account account = new Account();
        account.setAccountId("1");
        account.setEmailAddress("user1@conexiona.com");
        account.setAccountName("account1");
        account.setAddress("Calle Corunha- Parque Tecnoloxico");
        account.setCompany("Conexiona");
        account.setDicomId("123");
        account.setEnabled(1);

        ResponseEntity<Account> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/accounts", account, Account.class);
        assertNotNull(postResponse1);
        assertNotNull(postResponse1.getBody());

        User user = new User();
        user.setAccount(account);
        user.setUserId("1");
        user.setEmailAddress("user1@conexiona.com");
        user.setUserName("user1");
        user.setPassword("321");
        user.setEnabled(1);
        user.setLastLogin(new String("2020-07-28 00:00:00"));

        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    public void testCreateUser2() {
        Account account = new Account();
        account.setAccountId("2");
        account.setEmailAddress("user1@conexiona.com");
        account.setAccountName("account2");
        account.setAddress("Calle Corunha- Parque Tecnoloxico");
        account.setCompany("Conexiona");
        account.setDicomId("123");
        account.setEnabled(1);

        ResponseEntity<Account> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/accounts", account, Account.class);
        assertNotNull(postResponse1);
        assertNotNull(postResponse1.getBody());

        User user = new User();
        user.setAccount(account);
        user.setUserId("1");
        user.setEmailAddress("user1@conexiona.com");
        user.setUserName("user2");
        user.setPassword("321");
        user.setEnabled(1);
        user.setLastLogin(new String("2020-07-28 00:00:00"));

        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testGetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById() {
        User user = restTemplate.getForObject(getRootUrl() + "/users/1", User.class);
        System.out.println(user.getUserName());
        assertNotNull(user);
    }

    @Test
    public void testUpdateUserPost() {
        int id = 1;
        User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        user.setUserName("admin1");
        user.setEmailAddress("hola@conexiona.com");

        restTemplate.put(getRootUrl() + "/users/" + id, user);

        User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        assertNotNull(updatedUser);
    }

    @Test
    public void testDeleteUserPost() {
        int id = 2;
        User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        assertNotNull(user);

        restTemplate.delete(getRootUrl() + "/users/" + id);

        try {
            user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
