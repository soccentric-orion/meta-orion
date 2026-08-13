SUMMARY = "Orion OS — production image for ST STM32MP1"
DESCRIPTION = "Full image with A/B OTA, gRPC/Protobuf, Orion-HAL, hardware \
interface tooling, and connectivity."
HOMEPAGE = "https://github.com/SiliconCentric/Orion-OS"
LICENSE = "MIT"

require recipes-extended/images/core-image-full-cmdline.bb
IMAGE_FEATURES += "ssh-server-openssh"

# Boot partition (p1) contents for bootimg-partition: the kernel image and the
# STM32MP1 device tree (both deployed under the kernel/ subdir).
IMAGE_BOOT_FILES = "kernel/${KERNEL_IMAGETYPE};${KERNEL_IMAGETYPE} kernel/stm32mp157f-dk2.dtb;stm32mp157f-dk2.dtb"

# OTA / swupdate — install the A/B runtime on the STM32MP1 discovery board
IMAGE_INSTALL:append:stm32mp15-disco = " swupdate swupdate-www orion-swupdate-config"

# Home partition init (persistent /home on the data partition)
IMAGE_INSTALL:append = " orion-data-init"

# Root filesystem expansion (single-partition deployments)
IMAGE_INSTALL:append = " expand-rootfs"

# Connectivity
IMAGE_INSTALL:append = " \
    orion-connectivity iw rfkill bluez5-dev \
"

# gRPC + Protobuf via packagegroup-orion-middleware
IMAGE_INSTALL:append = " packagegroup-orion-middleware sudo"

# Cloud/telemetry package group (not installed by default; add via
# packagegroup-orion-cloud when deployments need MQTT/NTP/cloud connectivity)
# IMAGE_INSTALL:append = " packagegroup-orion-cloud"

# Hardware interface tools
IMAGE_INSTALL:append = " \
    i2c-tools python3-smbus python3-smbus2 \
    spitools python3-spidev \
    libgpiod libgpiod-tools python3-gpiod \
    picocom minicom python3-pyserial \
    usbutils pciutils nvme-cli sg3-utils hdparm mtd-utils \
    can-utils \
    lshw ethtool devmem2 cpupower watchdog sysstat iotop strace \
    v4l-utils evtest python3-periphery vim \
"

# Orion stack
IMAGE_INSTALL:append = " orion-hal orion-services"

# Target debugging and profiling
IMAGE_FEATURES += "tools-debug"
IMAGE_INSTALL:append = " gdbserver perf trace-cmd valgrind"

# Ptest package and runner support
IMAGE_FEATURES += "ptest-pkgs"

# Operator account — locked, SSH-key access only
inherit extrausers
EXTRA_USERS_PARAMS = "\
    useradd -M -s /bin/bash -G sudo orion; \
    usermod -L orion; \
"
