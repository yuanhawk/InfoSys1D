package tech.sutd.pickupgame.data;

import tech.sutd.pickupgame.data.ui.helper.NewRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.PastRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.UpcomingRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.UserRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;

public interface DataManager extends YourRoomHelper, UserRoomHelper, NewRoomHelper,
        UpcomingRoomHelper, PastRoomHelper {
}
