package com.se300.ledger.complete;

import com.se300.ledger.*;
import com.se300.ledger.command.CommandProcessor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;


// Import Smart Store classes for testing
import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import edu.se300.smartstore.service.StoreService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompleteTest {
    Ledger testLedger;

    
    @ParameterizedTest
    @Order(1)
     void parameterizedValueSourcesTest(String address) throws LedgerException {
         // create a fresh ledger each time so tests don’t interfere
         Ledger ledger = Ledger.getInstance("test-ledger", "desc", "seed");
         ledger.reset();
     
         // try making a new account
         Account acc = ledger.createAccount(address);
     
         // basic checks
         assertNotNull(acc);
         assertEquals(address, acc.getAddress());
     
         // making the same account again should fail
         assertThrows(LedgerException.class, () -> ledger.createAccount(address));
     }

    
    @Test
        @Order(2)
        @ParameterizedTest
        @CsvSource({"mary, 1000", "bob, 1000", "bill, 1000", "frank, 1000", "jane, 0"})
        void parameterizedComplexSourcesTest(String address, int balance) throws LedgerException {
            // set up a new ledger for each test so they don’t share data
            Ledger ledger = Ledger.getInstance("complex-ledger", "desc", "seed");
            ledger.reset();
       
            // make a new account and assign a balance
            Account acc = ledger.createAccount(address);
            acc.setBalance(balance);
       
            // check that everything worked right
            assertNotNull(acc);
            assertEquals(address, acc.getAddress());
            assertEquals(balance, acc.getBalance());
       
            // try creating the same account again to make sure it throws an error
            assertThrows(LedgerException.class, () -> ledger.createAccount(address));
        }
        // TODO: Complete this test to demonstrate parameterized testing with complex sources like CSV, method sources, etc.

    
    @Test
    @Order(3)
    @RepeatedTest(3)
    void repeatedTest(RepetitionInfo info) throws LedgerException {
            Ledger ledger = Ledger.getInstance("test-ledger", "desc", "seed");
            ledger.reset();
       
            Account payer = ledger.createAccount("payer");
            payer.setBalance(10_000);
            Account recv = ledger.createAccount("receiver");
       
            for (int i = 1; i <= 10; i++) {
                ledger.processTransaction(new Transaction(
                    "r" + info.getCurrentRepetition() + "-" + i,
                    1, 10, "n", payer, recv
                ));
            }
       
            assertEquals(1, ledger.getNumberOfBlocks());
            assertNotNull(ledger.getBlock(1));
        }
    
    @BeforeEach
        void setUp() throws LedgerException {
            // make a new ledger before each test
            testLedger = Ledger.getInstance("life-ledger", "desc", "seed");
            testLedger.reset();
            testLedger.createAccount("mary");
        }
        // TODO: Complete this setup method for lifecycle demonstration
   


        @AfterEach void tearDown() {
            // clear the ledger after each test
            testLedger = null;
        // TODO: Complete this teardown method for lifecycle demonstration
    }
    

    @Test
    @Order(4)
    void lifeCycleTest() throws LedgerException {
    assertNotNull(testLedger);
   
    Account acc = testLedger.getUncommittedBlock().getAccount("mary");
    assertNotNull(acc);
   
    assertEquals(0, testLedger.getNumberOfBlocks());
    }
    
    
    @Order(5)
    @Test
    @EnabledIfSystemProperty(named = "RUN_GETSET", matches = "true")
    void conditionalTest() throws LedgerException {
    boolean enabled = Boolean.getBoolean("RUN_GETSET")
        || "true".equalsIgnoreCase(System.getenv("RUN_GETSET"));
    assumeTrue(enabled, "Run with -DRUN_GETSET=true or env RUN_GETSET=true");
        
    Account acc = new Account("mary", 0);
    acc.setBalance(50);
    assertEquals("mary", acc.getAddress());
    assertEquals(50, acc.getBalance());
        
    Block block = new Block(1, "hash0");
    block.setHash("hash1");
    assertEquals("hash1", block.getHash());
        
    Transaction t = new Transaction("t1", 5, 1, "note", acc, acc);
    assertEquals("t1", t.getTransactionId());
    assertNotNull(t.toString());
}
    
    @Tag("Basic")
    @Order(6)
    @Test
    void taggedTest() throws LedgerException {
        Ledger ledger = Ledger.getInstance("tag-ledger", "desc", "seed");
        ledger.reset();

        Account acc = ledger.createAccount("bob");
        acc.setBalance(100);

        assertEquals("bob", acc.getAddress());
        assertEquals(100, acc.getBalance());
        assertTrue(acc.getBalance() > 0);
}
    
    @Nested
    class NestedTestClass {
        
        @Nested
        class ProductInitializationTests {
            
            @Test
            void validProductCreation() {
                Product product = new Product("SKU-001", "Widget", 9.99);
                assertEquals("SKU-001", product.getSku());
                assertEquals("Widget", product.getName());
                assertEquals(9.99, product.getPrice());
            }
            
            @Test
            void priceShouldBePositive() {
                Product product = new Product("SKU-002", "Gadget", 14.99);
                assertTrue(product.getPrice() > 0);
            }
        }
        
        @Nested
        class ProductEdgeCaseTests {
            
            @Test
            void emptyNameShouldBeAllowed() {
                Product product = new Product("SKU-003", "", 5.00);
                assertEquals("", product.getName());
            }
            
            @Test
            void zeroPriceShouldBeAllowed() {
                Product product = new Product("SKU-004", "Freebie", 0.00);
                assertEquals(0.00, product.getPrice());
            }
        }
    }
    
    @Test
    @Order(7)
    void basicAssertionsTest() {
        // Demonstrates basic assertions (assertEquals, assertTrue, assertFalse, etc.)
        Product product = new Product("SKU-001", "Widget", 9.99);
        
        assertEquals("SKU-001", product.getSku());
        assertEquals("Widget", product.getName());
        assertEquals(9.99, product.getPrice());
        assertNotNull(product);
        assertTrue(product.getPrice() > 0);
    }
    
    @Test
    @Order(8)
    void advancedAssertionsTest() {
        // Demonstrates advanced assertions (assertAll, assertThrows, assertTimeout, etc.)
        Inventory inv = new Inventory();
        inv.addProduct(new Product("SKU-001", "Widget", 9.99));
        inv.addProduct(new Product("SKU-002", "Gadget", 14.99));
        inv.updateStock("SKU-001", 10);
        inv.updateStock("SKU-002", 5);
        
        assertAll("Inventory checks",
            () -> assertEquals(15, inv.getStock("SKU-001") + inv.getStock("SKU-002")),
            () -> assertTrue(inv.hasProduct("SKU-001")),
            () -> assertThrows(IllegalArgumentException.class, () -> inv.getStock("SKU-999"))
        );
    }
    
    @Test
    @Order(9)
    void mockBehaviorTest() {
        // Demonstrates configuring mock behavior (when/then, doReturn/when, etc.)
        Inventory inv = mock(Inventory.class);
        when(inv.getStock("SKU-001")).thenReturn(42);
        
        StoreService store = new StoreService(inv);
        int stock = store.currentStock("SKU-001");
        
        assertEquals(42, stock);
    }
    
    @Test
    @Order(10)

    void assumptionsTest() {
    // removed stray @Test/method declaration; assertions run inside assumptionsTest()() {
        Product product = new Product("SKU-001", "Widget", 9.99);

        assumeTrue(product.getSku().startsWith("SKU-"));
        assertEquals("Widget", product.getName());
    }
        
@Test
    void skipIfPriceIsZero() {
        Product product = new Product("SKU-002", "Freebie", 0.00);

        assumeFalse(product.getPrice() == 0.00);
        assertTrue(product.getPrice() > 0);
    }

@Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "dev")
    void onlyRunInDevEnvironment() {
        Product product = new Product("SKU-003", "DevTool", 19.99);
        assertEquals("DevTool", product.getName()); 
    }

    
    
    @Test
    @Order(11)
    void mockVerificationTest() {
        // Demonstrates verifying mock interactions (verify, times, never, etc.)
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);
        
        Product product = new Product("SKU-001", "Widget", 9.99);
        store.registerProduct(product);
        
        verify(inv, times(1)).addProduct(ArgumentMatchers.any());
    }
    
    @Test
    @Order(12)
    void mockArgumentMatchersTest() {
        // Demonstrates using argument matchers with mocks (any(), eq(), etc.)
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);
        
        store.currentStock("SKU-ABC");
        
        verify(inv).getStock(ArgumentMatchers.eq("SKU-ABC"));
    }
    
    @Test
    @Order(13)
    void methodOrderTest_A_add() {
        // Demonstrates test method ordering using @TestMethodOrder and @Order annotations
        staticInventory.addStock("SKU-001", 2);
        assertEquals(2, staticInventory.getStock("SKU-001"));
    }
    
    @Test
    @Order(14)
    void methodOrderTest_B_remove() {
        // Continues ordered test sequence
        staticInventory.removeStock("SKU-001", 1);
        assertEquals(1, staticInventory.getStock("SKU-001"));
    }
    
    @Test
    @Order(15)
    void endToEndLedgerTest() {
       Ledger ledger = new Ledger();
        ledger.reset(); // clears before
        ledger.createAccount("Alice");
        ledger.createAccount("Bob");

        // Act
        ledger.deposit("Alice", 100.00);
        ledger.transfer("Alice", "Bob", 40.00);
        ledger.deposit("Bob", 10.00);
        ledger.validateLedger(); /

        // Assert
        assertEquals(60.00, ledger.getBalance("Alice"), 0.01);
        assertEquals(50.00, ledger.getBalance("Bob"), 0.01);

        Transaction lastTx = ledger.getLastTransaction();
        assertNotNull(lastTx);
        assertEquals("Bob", lastTx.getReceiver());
        assertEquals(10.00, lastTx.getAmount(), 0.01);

        assertTrue(ledger.isValid());
    }
    
    // Additional comprehensive Mockito tests
    @Test
    @Order(16)
    void comprehensiveMockitoTests_Behavior() {
        Inventory inv = mock(Inventory.class);
        when(inv.getStock("SKU-001")).thenReturn(42);
        StoreService store = new StoreService(inv);
        assertEquals(42, store.currentStock("SKU-001"));
    }
    
    @Test
    @Order(17)
    void comprehensiveMockitoTests_Verification() {
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);
        store.registerProduct(new Product("SKU-001", "Widget", 9.99));
        verify(inv, times(1)).addProduct(ArgumentMatchers.any());
    }
    
    @Test
    @Order(18)
    void comprehensiveMockitoTests_ArgumentMatchers() {
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);
        store.currentStock("SKU-ABC");
        verify(inv).getStock(ArgumentMatchers.eq("SKU-ABC"));
    }
}
