package metachess.library;

import java.awt.Component;

/** Interface of a Resource List
 * @author Agbeladem (7DD)
 * @version 0.8.0
 */
public interface ResourceList {

    /** (Re)initialize this resource list (required) */
    public void init();

    /** Get the name of the selected resource
     * @return the name
     */
    public String getName();

    /** Get a component of this list
     * @return the component
     */
    public Component getComponent();

}


