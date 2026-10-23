# rogo_sdk_lib

Đóng gói Raio-Android-SDK (`ro_sdk`) thành thư viện JitPack: `implementation("com.github.xtung2404:rogo_sdk_lib:<tag>")`.
Bản SDK nằm trong `rogosdk-lib/libs/`, cập nhật bằng lệnh `/build-sdk` của Raio-Android-SDK.

## SDK đang đóng gói
- File: `raio-android-sdk-261023-release.aar` — build ngày 23/10/2026
- Build từ commit: `ad13d3f` (nhánh `main`), kèm thay đổi chưa commit: `ro_sdk/build.gradle.kts`,
  `api/sync/CloudSyncDevice.java`, `base/RoSDKHandler.java`, `impl/DeviceHandlerImpl.java`, `impl/RoSdkHandlerImpl.java`,
  xoá `libs/rogobase.jar`, `libs/rogobaseandroid-release.aar`, `libs/rogobaseapp.jar`
- Tag phát hành: `1.0.2` — `implementation("com.github.xtung2404:rogo_sdk_lib:1.0.2")`

## Lịch sử thay đổi

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
