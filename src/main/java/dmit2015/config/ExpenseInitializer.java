package dmit2015.config;

import dmit2015.service.ExpenseJpaService;
import dmit2015.model.Expense;
import dmit2015.service.ExpenseJpaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import net.datafaker.Faker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class ExpenseInitializer {
    private final Logger logger = Logger.getLogger(ExpenseInitializer.class.getName());

    @Inject
    private ExpenseJpaService expenseJpaService;


    /**
     * Using the combination of `@Observes` and `@Initialized` annotations, you can
     * intercept and perform additional processing during the phase of beans or events
     * in a CDI container.
     * <p>
     * The @Observers is used to specify this method is in observer for an event
     * The @Initialized is used to specify the method should be invoked when a bean type of `ApplicationScoped` is being
     * initialized
     * <p>
     * Execute code to create the test data for the entity.
     * This is an alternative to using a @WebListener that implements a ServletContext listener.
     * <p>
     * ]    * @param event
     */
    public void initialize(@Observes @Initialized(ApplicationScoped.class) Object event) {
        logger.info("Initializing expenses");

        if (expenseJpaService.count() == 0) {
            /* You have three options for creating the test data:
                Option 1) Hard code the test data when testing a small dataset.
                Option 2) Read the test data from a text file when testing a large dataset.
                Option 3) Generate the test data using DataFaker.
                          When used with Integration Testing you will need to save the generated data
                          to a file that can be read later to compare with expected values.
             */

            try {
                // TODO: Create a new entity instance
                // TODO: Set the properties of the entity instance
                // TODO: Add the entity instance to the JPA repository

                var faker = new Faker();
                for(int count = 1; count <= 99; count++) {
                    Expense newExpense = Expense.of(faker);
                    expenseJpaService.createExpense(newExpense);
                }
                // Starter code to read data from a text file one line at a time.
//                    try (var reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/data/filename.csv"))))) {
//                        String line;
//                        // TODO: Uncomment the line before if the first line in the CSV file contains column headings
////                    reader.readLine();
//                        while ((line = reader.readLine()) != null) {
//                            Optional<Expense> optionalExpense = Expense.parseCsv(line);
//                            if (optionalExpense.isPresent()) {
//                                Expense csvExpense = optionalExpense.orElseThrow();
//                                try {
//                                    expenseJpaService.createExpense(csvExpense);
//                                } catch (Exception ex) {
//                                    String message = String.format("Failed to create username %s", csvExpense.getUsername());
//                                    logger.log(Level.SEVERE, message, ex);
//                                }
//                            }
//                        }
//                    }


            } catch (Exception ex) {
                logger.warning(ex.getMessage());
            }

            logger.info("Created " + expenseJpaService.count() + " records.");
        }
    }
}