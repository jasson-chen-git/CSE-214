package hw6;

import java.io.Serializable;

/**
 * The <code>Storage</code> class implements a <code>Storage</code> object.
 *
 * @author Jason Chen
 *      112515450
 *      jason.chen.5@stonybrook.edu
 *      Recitation 4
 *      Homework 6
 */
public class Storage implements Serializable {
    private static long serialVersionUID;
    private int id;
    private String client, content;

    /**
     * Returns an instance of <code>Storage</code>.
     *
     * Creates a new instance if <code>Storage</code> with the given parameters.
     *
     * @param id
     *      The id of the storage box
     * @param client
     *      The client that the storage box belongs to
     * @param content
     *      The content of the storage box
     */
    public Storage(int id, String client, String content) {
        setId(id);
        setClient(client);
        setContent(content);
    }

    /**
     * Returns the id of the storage box.
     *
     * @return
     *      The id of the storage box
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the storage box.
     *
     * @param id
     *      The id of the storage box
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the client that the storage box belongs to.
     *
     * @return
     *      The client that the storage box belongs to
     */
    public String getClient() {
        return client;
    }

    /**
     * Sets the client that the storage box belongs to.
     *
     * @param client
     *      The client that the storage box belongs to
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Returns the content of the storage box.
     *
     * @return
     *      The content of the storage box
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the storage box.
     *
     * @param content
     *      The content of the storage box
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the <code>Storage</code> object as a string.
     *
     * @return
     *      The string representing the <code>Storage</code> object
     */
    public String toString() {
        return String.format("%-10d%-35s%-20s", id, content, client);
    }
}
