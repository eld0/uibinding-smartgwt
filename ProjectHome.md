**Updated to GWT 2.6.1 and Smartgwt 4.1p - 30 jun 2014**

# Download the last version: #

**[uibinding-smartgwt-1.0.11-SMARTGWT-4.1p.jar](https://docs.google.com/uc?authuser=0&id=0B75N-bGMA0iRWExMUHBtcWdBRms&export=download)**

**[smartgwt-4.1p.jar](https://docs.google.com/uc?id=0B75N-bGMA0iRMUN3d1VyZjNhQ1k&export=download)**

**[smartgwt-skins-4.1p.jar](https://docs.google.com/uc?id=0B75N-bGMA0iReXlGZlFxSXdQaFk&export=download)**

## Using SmartGWT objects with UiBinder ##
This project adapts Smart GWT visual classes to enable them to be used as UiBinder nodes.

## Sharing space between GWT and Smart GWT ##
Smart GWT visual objects are primarily extension classes of Smart GWT Canvas. Non-Canvas objects has to be added as members of a Canvas. Smart GWT Canvas class is itself already an extension of GWT Widget class.

SmartGWT Canvas widgets do not share space well with non-SmartGWT widgets. The SmartGWT canvas would push itself in-front to obstruct the visibility of GWT widgets. To share a space between the two sets of widgets, either
  * the page design needs to carefully avoid having GWT widgets intersecting into SmartGWT canvas, vice versa. Especially that any part of a GWT sub-menu or pop-up that pops into SmartGWT canvas space would not be visible.
  * all non-SmartGWT widgets must be embedded inside a SmartGWT WidgetCanvas.
You should not add a Canvas to a non-SmartGWT Widget and expect it to be displayed properly.

## UiBinder requirements and constraints ##
The following are requirements for a class that is to be used as a UiBinder node.
  * A class to be used as a parent node of GWT Widgets must implement HasWidget, and any class that are its candidate child nodes must be extension classes of GWT Widget.
  * A class to be used as parent node of HTML contents must implement HasHTML.
  * A class to be used as a node for encapsulating text content must implement HasText.

### Caveat ###
A class can effectively implement only one of HasWidget, HasHTML or HasText. Reasons are:
  * HasHTML is already an extension of HasText. No point in implementing HasText when HasHTML is implemented.
  * The GWT compiler ignores HasHTML or HasText when HasWidget is implemented. Therefore, a custom UiBinder node cannot have a mix of Widget nodes with HTML and Text node.

However, there are Widgets native to GWT usable as UiBinder nodes which do not face the above constraints or caveat. The reason being, GWT compiler processes them with their individualised UiBinder parser. Currently, as of GWT 2.1M1, GWT compiler does not provide any means to let us attach custom UiBinder parsers to the GWT compilation process. To attach custom UiBinder parsers, we have to rebuild GWT from source.

For most of them, you cannot extend UiBinder native classes that is compiled using their own individualised parser, unless your extension class package hijacks the com.google.gwt.user.client.ui namespace and delude the parser into accepting your extension class as the UiBinder native class.

### Adapting SmartGWT for UiBinder ###
  * Extend a Canvas to implement HasWidget, HasText or HasHTML.
  * For non-Canvas visual objects: Create an extension of Widget, as a wrapper, which implements UIMasquerade (as well as to optionally implement HasWidget, HasText or HasHTML if it is intended to have parent node capabilities). Embed the non-Canvas object in the wrapper.

The add(Widget) method of a class intended to be used as a UiBinder parent node must therefore be adapted to expect both Canvas and non-Canvas child nodes. If a child node implements UIMasquerade, the parent node's add(Widget) method is to get the embedded object from the child node using UIMasquerade.getSmartObject() and add the embedded object, and not the child node Widget, as a member.

### Dire warning ###
Do not lay any SmartGWT construct into a plain GWT widget. If your smartGWT construct does not display, it means you have to hunt for a plain GWT widget you do not realise is hidden somewhere between the root panel and your SmartGWT construct.
  * Adding a SmartGWT canvas onto a GWT widget would cause the GWT widget to be obscured - which is the root of the problem of mixing SmartGWT and plain GWT.
  * You can add a plain GWT widget onto a SmartGWT canvas, because the add(Widget) function of a SmartGWT canvas spontaneously wraps that widget with a WidgetCanvas.

However, you should be able to add SmartGWT canvases onto a non-visible plain GWT widget as long the plain GWT widget's being obscured is inconsequential. For example, it does not matter if a GWT SimplePanel or layout is obscured as long as you do not have to detect mouse/keyboard events for those GWT widgets.