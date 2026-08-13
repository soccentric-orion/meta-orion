# os-release_%.bbappend — identify this image as "Orion OS".
DISTRO_NAME  = "Orion OS"
DISTRO_VERSION = "${PV}"
PRETTY_NAME = "Orion OS ${PV}"
OS_RELEASE_FIELDS:append = " ID_LIKE"
ID_LIKE = "poky"

do_install:append() {
    cat > ${D}${sysconfdir}/os-release <<OSEOF
ID=orion
ID_LIKE=poky
NAME="Orion OS"
VERSION="${PV}"
VERSION_ID="${PV}"
PRETTY_NAME="Orion OS ${PV}"
HOME_URL="https://orion.dev/"
SUPPORT_URL="https://orion.dev/support"
BUG_REPORT_URL="https://orion.dev/issues"
OSEOF
}
