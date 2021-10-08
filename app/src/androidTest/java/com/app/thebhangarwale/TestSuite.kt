package com.app.thebhangarwale

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    SplashActivityTest::class,
    PhoneNumberActivityTest::class,
    OtpActivityTest::class
)
class TestSuite