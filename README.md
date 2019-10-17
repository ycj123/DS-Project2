# DS-Project3
Group: FRIDAY430
- 9.13 - 9.20
  CJY : ServerView
  YFD : Service
  ZL  : RMI,DAO
  JXQ : RMI,Client/ClientManager
##Meetings
- 9.20 4PM Meeting at Baillieu Ground Floor
- 9.24 3PM Meeting at Baillieu Ground Floor

## Shape in RMI:
- Line 0 : 
    - setStartX, setStartY
    - setEndX, setEndY
- circle 1:
    - circle.setCenterX(sX + (eX - sX)/2);
    - circle.setCenterY(sY + (eY - sY)/2);
    - circle.setRadius(radius);
    - circle.setFill(Color.TRANSPARENT);
    - circle.setStroke(Properties.getForeColor());
    - circle.setStrokeWidth(Properties.getWidth());
- ellipse 2:
    - ellipse.setCenterX(sX + (eX - sX)/2);
    - ellipse.setCenterY(sY + (eY - sY)/2);
    - ellipse.setRadiusX(dx);
    - ellipse.setRadiusY(dy);
    - ellipse.setFill(Color.TRANSPARENT);
    - ellipse.setStroke(Properties.getForeColor());
    - ellipse.setStrokeWidth(Properties.getWidth());
- rectangle 3:
    -