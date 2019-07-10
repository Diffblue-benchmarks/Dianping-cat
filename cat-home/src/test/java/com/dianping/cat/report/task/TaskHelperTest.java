/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dianping.cat.report.task;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskHelperTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testJoinIntArrayChar() {
        Assert.assertEquals("1,2", TaskHelper.join(new int[]{1, 2}, ','));
    }

    @Test
    public void testYesterdayZero() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date yesterdayZero = TaskHelper.yesterdayZero(cal.getTime());
        Assert.assertEquals(cal.getTimeInMillis(),
                yesterdayZero.getTime() + 3600L * 1000 * 24);
    }

    @Test
    public void testYesterdayZero2() {
        Assert.assertEquals(new Date(1562626800000L),
                TaskHelper.yesterdayZero(new Date()));
    }

    @Test
    public void testTodayZero() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        Date todayZero = TaskHelper.todayZero(cal.getTime());
        Assert.assertEquals(cal.getTimeInMillis(), todayZero.getTime() + 1L);
    }

    @Test
    public void testTodayZero2() {
        Assert.assertEquals(new Date(1562713200000L),
                TaskHelper.todayZero(null));
        Assert.assertEquals(new Date(1562713200000L),
                TaskHelper.todayZero(new Date()));
    }

    @Test
    public void testJoin1() {

        final int[] array = {1, -8, -7, -7, -7, -7, -7, -253_968, -7, -24, -24,
                -2147475464, 4088, -2147483647, 9, 33505273, 1, -8};


        Assert.assertNull(TaskHelper.join((int[]) null, '\u0000',
                -160729767, -159952269));
        Assert.assertNull(TaskHelper.join((Number[]) null, '\u0000',
                -1, 2147483647));
        Assert.assertNull(TaskHelper.join((double[]) null, '\u0000',
                -402653175, 1669333001));

        Assert.assertEquals("", TaskHelper.join(new int[0], '\u0000'));
        Assert.assertEquals("", TaskHelper.join(new double[]{5.1}, '\u0000'));
        Assert.assertEquals("", TaskHelper.join(new int[0], '\u0000',
                -2147483648, 0));
        Assert.assertEquals("", TaskHelper.join(new int[0], '\u0000',
                1816002434, -1402208264));
        Assert.assertEquals("", TaskHelper.join(new Number[]{null}, '\u0000',
                2147483647, 1073742086));
        Assert.assertEquals("", TaskHelper.join(new double[]{
                        5.1, 3.5}, '\u0000',
                -450887681, -1299185664));
        Assert.assertEquals("2.1\u00004.5",
                TaskHelper.join(new double[]{2.1, 4.5}, '\u0000'));
        Assert.assertEquals("", TaskHelper.join(new double[]
                {5.1, 9.2}, '\u0000', 1197600769, 964623358));
        Assert.assertEquals("", TaskHelper.join(new Number[]{null},
                '\u0000'));
        Assert.assertEquals("-100\u0000-8912895", TaskHelper.join(new Number[]
                {-100, -8912895}, '\u0000', 0, 1));
        Assert.assertEquals("", TaskHelper.join(new Number[]{null}, '\u0000',
                139524095, -1744830464));
        Assert.assertEquals("1\u0000-8", TaskHelper.join(array, '\u0000',
                16, 17));
    }

    @Test
    public void testJoin2() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        TaskHelper.join(new Number[]{null}, '\u0000',
                1071661082, 1071661083);
    }

    @Test
    public void testJoin3() {
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        TaskHelper.join(new Number[]{-100, -8912895}, '\u0000',
                0, 1140850714);
    }

    @Test
    public void testNextMonthStart() {
        Assert.assertEquals(new Date(1554678000000L),
                TaskHelper.nextMonthStart(new Date(1552044153000L)));
    }

    @Test
    public void testThisHour() {
        Assert.assertEquals(new Date(1562767200000L),
                TaskHelper.thisHour(new Date(1562767504000L)));
    }

    @Test
    public void testTomorrowZero() {
        Assert.assertEquals(new Date(1562799600000L),
                TaskHelper.tomorrowZero(null));
        Assert.assertEquals(new Date(1562799600000L),
                TaskHelper.tomorrowZero(new Date()));
    }
}
