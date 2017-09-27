package sg.edu.nus.iss.phoenix.entity;

/**
 *
 * @author divah
 */
public class RPSearchObject {
	  private String name;
	    private String description;

    /**
     *
     */
    public RPSearchObject() {
			super();
		}

    /**
     *
     * @param name
     * @param description
     */
    public RPSearchObject(String name, String description) {
			super();
			this.name = name;
			this.description = description;
		}

    /**
     *
     * @return
     */
    public String getName() {
			return name;
		}

    /**
     *
     * @param name
     */
    public void setName(String name) {
			this.name = name;
		}

    /**
     *
     * @return
     */
    public String getDescription() {
			return description;
		}

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
			this.description = description;
		}
}
