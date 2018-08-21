package org.zuhlke;

import org.junit.Assert;
import org.junit.Test;

public class ScannerTest {

    @Test(expected = Exception.class)
    public void addItem_itemRepositoryEmpty_throws() {
        // Given
        ItemRepository repo = new ItemRepository();
        Scanner scanner = new Scanner(repo);

        // When
        scanner.addItem("10101");

        // Then (exception)
    }

    @Test
    public void addItem_itemRepositoryHasItem_returnsSummary() {
        // Given
        ItemRepository repo = new ItemRepository();
        repo.add("10101", "Wine", "6.66");
        Scanner scanner = new Scanner(repo);

        // When
        String summary = scanner.addItem("10101");

        // Then
        Assert.assertEquals("Wine: 6.66\nTotal: 6.66", summary);
    }

    @Test
    public void removeItem_itemRepositoryHasItem_returnsSummary() {
        // Given
        ItemRepository repo = new ItemRepository();
        repo.add("10101", "Wine", "6.66");
        Scanner scanner = new Scanner(repo);
        scanner.addItem("10101");

        // When
        String summary = scanner.removeItem("10101");

        // Then
        Assert.assertEquals("Total: 0.00", summary);
    }

}
