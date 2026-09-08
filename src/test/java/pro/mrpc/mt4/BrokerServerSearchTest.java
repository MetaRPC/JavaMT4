package pro.mrpc.mt4;

import pro.mrpc.mt4.utils.BrokerServerSearch;
import pro.mrpc.mt4.utils.BrokerServerSearch.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for {@link BrokerServerSearch}.
 * <p>
 * Requires network access to the MetaQuotes broker network.
 * Set the environment variable {@code MT4_BROKER_SEARCH=true} to enable.
 */
class BrokerServerSearchTest {

    @BeforeAll
    static void checkEnabled() {
        Assumptions.assumeTrue(
                "true".equalsIgnoreCase(System.getenv("MT4_BROKER_SEARCH")),
                "Skipping: set MT4_BROKER_SEARCH=true to run broker search integration test"
        );
    }

    @Test
    @DisplayName("Search for brokers by company name")
    void testSearch() throws Exception {
        SearchResult result = BrokerServerSearch.search("MetaQuotes");

        assertNotNull(result, "SearchResult should not be null");
        assertNotNull(result.result, "result array should not be null");
        assertTrue(result.result.length > 0, "Should find at least one broker");

        for (BrokerResult broker : result.result) {
            System.out.println("Broker: " + broker.company);
            assertNotNull(broker.results, "results array should not be null");
            for (ServerResult server : broker.results) {
                System.out.println("  Server: " + server.name);
                System.out.println("    Access: " + java.util.Arrays.toString(server.access));
                System.out.println("    Demo: " + server.is_demo);
                assertNotNull(server.name, "Server name should not be null");
                assertNotNull(server.access, "Server access should not be null");
                assertTrue(server.access.length > 0, "Server should have at least one access address");
            }
        }
    }
}
