package hw6;

import java.io.Serializable;
import java.util.*;

/**
 * The <code>StorageTable</code> class implements a hash table of <code>Storage</code> objects.
 *
 * @author Jason Chen
 *      112515450
 *      jason.chen.5@stonybrook.edu
 *      Recitation 4
 *      Homework 6
 */
public class StorageTable extends Hashtable implements Serializable {
    private static int serialVersionUID;
    private Hashtable<Integer, Storage> storageTable = new Hashtable<>();

    /**
     * Returns an instance of <code>StorageTable</code>.
     *
     * The default constructor takes no parameters.
     */
    public StorageTable() {
    }

    /**
     * Inserts a new <code>Storage</code> object into the hash table.
     *
     * @param storageID
     *      The key associated with the <code>Storage</code> object
     * <dt>Precondition:
     *      <dd><code>storageID</code> greater than or equal to 0 and
     *      does not already exist in the table</dd></dt>.
     *
     * @param storage
     *      The <code>Storage</code> object
     * <dt>Precondition:
     *      <dd><code>storage</code> is not null</dd></dt>.
     *
     * <dt>Postcondition:
     *      <dd><code>item</code> has been added into the hash table</dd></dt>.
     */
    public void putStorage(int storageID, Storage storage) {
        if(storageID < 0 || storage == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        if(storageTable.containsKey(storageID)) {
            throw new IllegalArgumentException("Box " + storageID + " already exists.");
        }
        storageTable.put(storageID, storage);
    }

    /**
     * Returns a <code>Storage</code> object with the given key on the hash table.
     *
     * @param storageID
     *      The key of the <code>Storage</code> object
     *
     * @return
     *      The <code>Storage</code> object with the given key, null otherwise
     *
     * @throws IllegalArgumentException
     *      Indicates that a <code>Storage</code> object is not found with the given key
     */
    public Storage getStorage(int storageID) {
        if(!storageTable.containsKey(storageID)) {
//            return null;      //  exception thrown instead.
            throw new IllegalArgumentException("Box " + storageID + " not found in storage.");
        }
        return storageTable.get(storageID);
    }

    /**
     * Prints all <code>Storage</code> objects maintained by <code>StorageTable</code> in
     * the hash table.
     *
     * If the hash table is empty, a different message will be printed instead.
     */
    public void printStorageTableAll() {    //  for option P
        if(storageTable.isEmpty()) {
            System.out.println("Storage is empty.");
        }
        else {
            System.out.printf("%-10s%-35s%-20s%n", "Box#", "Contents", "Owner");
            System.out.println("----------------------------------------------------------------");
            for(int key : storageTable.keySet()) {
                System.out.println(storageTable.get(key).toString());
            }
        }
    }

    /**
     * Returns the removed <code>Storage</code> object with the given key.
     *
     * @param storageID
     *      The key of the <code>Storage</code> object
     *
     * @return
     *      The <code>Storage</code> object with the given key
     *
     * <dt>Postcondition:
     *      <dd><code>Storage</code> object is removed from the hash table if it
     *      exists</dd></dt>.
     *
     * @throws IllegalArgumentException
     *      Indicates that a <code>Storage</code> object is not found with the given key
     */
    public Storage removeStorage(int storageID) {
        if(!storageTable.containsKey(storageID)) {
            throw new IllegalArgumentException("Item does not exist in storage.");
        }
        return storageTable.remove(storageID);
    }
    /**
     * Prints all <code>Storage</code> objects maintained by <code>StorageTable</code>
     * with the given client.
     *
     * If the hash table is empty, a different message will be printed instead.
     *
     * @param client
     *      The client of the <code>Storage</code> objects
     */
    public void printStorageTableSelect(String client) {    //  for option C
        if(storageTable.isEmpty()) {
            System.out.println("Storage is empty.");
        }
        else {
            System.out.printf("%-10s%-35s%-20s%n", "Box#", "Contents", "Owner");
            System.out.println("----------------------------------------------------------------");
            for(int key : storageTable.keySet()) {
                if (storageTable.get(key).getClient().compareToIgnoreCase(client) == 0) {
                    System.out.println(storageTable.get(key).toString());
                }
            }
        }
    }
}
