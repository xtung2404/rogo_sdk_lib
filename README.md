# rogo_sdk_lib

Đóng gói Raio-Android-SDK (`ro_sdk`) thành thư viện JitPack: `implementation("com.github.xtung2404:rogo_sdk_lib:<tag>")`.
Bản SDK nằm trong `rogosdk-lib/libs/`, cập nhật bằng lệnh `/build-sdk` của Raio-Android-SDK.

## SDK đang đóng gói
- File: `raio-android-sdk-261104-release.aar` — build ngày 04/11/2026
- Build từ commit: `3592eb7` (nhánh `main`), kèm thay đổi chưa commit: `ro_sdk/build.gradle.kts`,
  `app/callback/IoTDeviceReportCallback.java`, `base/DeviceHandler.java`, `base/StateHandler.java`,
  `impl/DeviceHandlerImpl.java`, `impl/StateHandlerImpl.java`, `service/RoSdkService.java`
- Core: `com.github.RogoSolutions:IotCoreAndroid:1.0.3.47`
- Tag phát hành: `1.0.3` — `implementation("com.github.xtung2404:rogo_sdk_lib:1.0.3")`

## Lịch sử thay đổi

### 1.0.3 (261104) — so với 1.0.2
- **Core**: đổi sang `com.github.RogoSolutions:IotCoreAndroid:1.0.3.47` (trước là `com.github.xtung2404:RogoCoreLib:1.0.3.38`);
  bỏ `hivemq-mqtt-client` khỏi POM (SDK không còn khai báo)
- **Hàm thêm**:
  - `DeviceHandler.verifyFilterCartridge(String, int, String, RequestResultCallback<IoTCartridgeVerifyResult>)`
  - `DeviceHandler.activateFilterCartridge(String, int, String, RequestResultCallback<IoTCartridgeActivateResult>)`
  - `DeviceHandler.getProductModelInfo(String, RequestResultCallback<IoTProductModel>)`
  - `StateHandler.getLogAttrBlocks(String, int, int, long, IoTGetLogAttrBlockCallback)`
  - `TokenHandler.callProjectApi(String, String, String, RequestResultCallback<String>)`
- **Hàm đổi chữ ký**:
  - `DeviceHandler.checkFirmwareVersion`: callback `IoTSoftwareCheckVersionCallback` → `IoTCheckSoftwareVersionCallback`
  - Kiểu trả về `IoTStateOnlineOffline` → `IoTOnlineStatus` ở `StateHandler.getOnlineStatusList` / `getOnlineStatusByMac` /
    `getOnlineStatusListWithDeviceId` / `getOnlineStatusByDeviceId` và `SharedDeviceStateHandler.getOnlineStatusByDeviceId` /
    `getOnlineStatusByLocationId`
- **Hàm đổi tên / xoá**: `StateHandler.getCurrentState(String, int, RequestResultCallback<IoTDeviceState>)` →
  `getCurrentReport(String, int, IoTGetDeviceStateCallback)`
- **Class**: thêm `api/ProjectConfig`, `api/sync` (`CloudSyncCartridge`, `CloudSyncPresence`); đổi package
  `api/link/state/onoffline` → `api/link/state/onlinestatus` (`APIGetOnlineStatus*`, `APIGetSharedOnlineStatus*`)
- **Commit**: `3592eb7` scan filter for kangaroo; `23c4898` Rework StateHandlerImpl log storage and rename online status APIs;
  `39510bf` handle get log from local database; `82b740a` Cache Record V2 log blocks in local database;
  `2eacf2f` Core 1.0.3.47 switch blocks, callProjectApi, Kangaroo online status; `f299238` Add StateHandler.getLogAttrBlocks;
  `f938e6b` Add setEnablePrint, return device detail via callback, rename AAR output

### 1.0.2 (261023) — so với 1.0.1
- **Hàm thêm** (`LocationHandler`, toạ độ / pin bản đồ cho location):
  - `createLocation(String label, String desc, Double latitude, Double longitude, ...)`: tạo location kèm toạ độ
    (truyền vĩ độ, kinh độ; SDK tự đổi sang [kinh độ, vĩ độ] khi gửi lên server). Cả hai `null` thì tạo không kèm toạ độ
  - `setLocationCoordinate(String locationId, double latitude, double longitude, ...)`: đặt hoặc dời pin, không đổi tên / mô tả
  - `clearLocationCoordinate(String locationId, ...)`: xoá pin khỏi bản đồ
- **Class thêm**: `api/link/location` (`APIUpdateLocationCoordinate`)
- **Commit**: `ad13d3f` Add optional location coordinate (map pin) support

### 1.0.1 (261023) — so với 1.0.0
- **Hàm thêm**:
  - `RoSDKHandler.setEnablePrint(Boolean)` — bật / tắt log của SDK
  - `RoSDKHandler.tokenHandler()` → handler mới `TokenHandler.getServiceToken(RequestResultCallback<RoServiceToken>)`
  - `RoSDKHandler.sharedDeviceStateHandler()` → handler mới `SharedDeviceStateHandler`:
    `getOnlineStatusByDeviceId(String, ...)`, `getOnlineStatusByLocationId(String, ...)` (trạng thái online của thiết bị được chia sẻ)
  - `StateHandler.registerDeviceReportCallback(String, IoTDeviceReportCallback)` / `unregisterDeviceReportCallback(String, IoTDeviceReportCallback)`
- **Hàm đổi tên** (`StateHandler`):
  - `getStateOnlineOfflineList` → `getOnlineStatusList`
  - `getStateOnlineOfflineByMac` → `getOnlineStatusByMac`
  - `getStateOnlineOfflineMapped` → `getOnlineStatusListWithDeviceId`
  - `getStateOnlineOfflineByDevice` → `getOnlineStatusByDeviceId`
- **Hàm xoá** (`StateHandler`):
  - `registerDeviceStateCallback(String)` / `unregisterDeviceStateCallback(String)`: thay bằng bản có `IoTDeviceReportCallback`
  - `getSharedStateOnlineOfflineByDevice` / `getSharedStateOnlineOfflineByLocation`: chuyển sang `SharedDeviceStateHandler`
- **Class thêm**: `base` (`TokenHandler`, `SharedDeviceStateHandler`), `impl` (2 impl tương ứng), `entity/token` (`RoServiceToken`),
  `api/link/token` (`APIGetServiceToken`), `api/sync` (`CloudSyncToken`), `app/callback` (`IoTDeviceReportCallback`)
- **Commit**: chưa có mốc commit của bản cũ
- Ghi chú: đổi tên file AAR từ `raio_sdk_1.0.0.aar` sang `raio-android-sdk-<yyMMdd>-release.aar`
