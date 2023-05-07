package pt.uc.sob.defektor.server.pluginization.pluginregistry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pt.uc.sob.defektor.common.data.FaultType;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.plugin.abstraction.InjektorPlug;
import pt.uc.sob.defektor.server.pluginization.PluginRegistry;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PluginRegistryTest {

    PluginRegistry pluginRegistry;
    InjektorPlug mockInjektor1,  mockInjektor2;
    FaultType mockFaultType1, mockFaultType2, mockFaultType3;
    TargetType mockTargetType1, mockTargetType2, mockTargetType3;

    @Before
    public void setUp() {
        setupMocks();
        setupPluginsOnRegistry();
    }

    @Test
    public void testRegisterInjektor() {
        // Arrange
        InjektorPlug thirdIjk = mock(InjektorPlug.class);
        when(thirdIjk.getName()).thenReturn("ThirdMock");
        when(thirdIjk.getFaultTypes()).thenReturn(List.of(new FaultType[]{mockFaultType2}));
        when(thirdIjk.getTargetTypes()).thenReturn(List.of(new TargetType[]{mockTargetType1}));

        // Act
        pluginRegistry.RegisterInjektor(thirdIjk);

        // Assert
        // an IJK is present as many times as the number of FaultTypes it supports
        assertEquals(4, pluginRegistry.count());
    }

    @Test
    public void testGetInjektorByName() {
        // Arrange
        // Act
        var ijk = pluginRegistry.getInjektorByName(mockFaultType1.getName(), mockInjektor1.getName());

        // Assert
        assertSame(mockInjektor1, ijk);
    }

    @Ignore("this test fails because it can't convert the mocked object to an actual injektor plug")
    @Test
    public void testGetInjektorByFaultTypeAndTarget() {
        // Arrange
        // Act
        var ijk = pluginRegistry.getInjektorByFaultTypeAndTarget(mockFaultType3.getName(), mockTargetType3.getName());

        // Assert
        assertSame(mockInjektor2, ijk);
    }

    private void setupMocks(){

        mockInjektor1 = mock(InjektorPlug.class);
        mockInjektor2 =  mock(InjektorPlug.class);

        mockFaultType1 = mock(FaultType.class);
        mockFaultType2 =  mock(FaultType.class);
        mockFaultType3 = mock(FaultType.class);

        mockTargetType1 =  mock(TargetType.class);
        mockTargetType2 = mock(TargetType.class);
        mockTargetType3 =  mock(TargetType.class);

        when(mockFaultType1.getName()).thenReturn("MockFaultType1");
        when(mockFaultType2.getName()).thenReturn("MockFaultType2");
        when(mockFaultType3.getName()).thenReturn("MockFaultType3");

        when(mockTargetType1.getName()).thenReturn("MockTargetType1");
        when(mockTargetType2.getName()).thenReturn("MockTargetType2");
        when(mockTargetType3.getName()).thenReturn("MockTargetType3");

        when(mockInjektor1.getName()).thenReturn("Mock1");
        when(mockInjektor1.getFaultTypes()).thenReturn(List.of(new FaultType[]{mockFaultType1, mockFaultType2}));
        when(mockInjektor1.getTargetTypes()).thenReturn(List.of(new TargetType[]{mockTargetType1}));

        when(mockInjektor2.getName()).thenReturn("Mock2");
        when(mockInjektor2.getFaultTypes()).thenReturn(List.of(new FaultType[]{mockFaultType3}));
        when(mockInjektor2.getTargetTypes()).thenReturn(List.of(new TargetType[]{mockTargetType2, mockTargetType3}));
    }

    private void setupPluginsOnRegistry(){
        pluginRegistry = new PluginRegistry();
        pluginRegistry.RegisterInjektor(mockInjektor1);
        pluginRegistry.RegisterInjektor(mockInjektor2);
    }

}