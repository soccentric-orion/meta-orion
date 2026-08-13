# Persistent journal + explicit NTP for Orion devices.
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://journald-orion.conf \
    file://timesyncd-orion.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/journald.conf.d
    install -m 0644 ${WORKDIR}/journald-orion.conf \
                    ${D}${sysconfdir}/systemd/journald.conf.d/orion.conf

    install -d ${D}${sysconfdir}/systemd/timesyncd.conf.d
    install -m 0644 ${WORKDIR}/timesyncd-orion.conf \
                    ${D}${sysconfdir}/systemd/timesyncd.conf.d/orion.conf
}

FILES:${PN} += " \
    ${sysconfdir}/systemd/journald.conf.d/ \
    ${sysconfdir}/systemd/timesyncd.conf.d/ \
"

INSANE_SKIP:${PN} += "empty-dirs"
