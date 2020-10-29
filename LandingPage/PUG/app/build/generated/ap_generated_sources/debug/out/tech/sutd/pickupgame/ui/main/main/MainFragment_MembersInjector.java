// Generated by Dagger (https://dagger.dev).
package tech.sutd.pickupgame.ui.main.main;

import com.bumptech.glide.RequestManager;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerFragment_MembersInjector;
import dagger.internal.InjectedFieldSignature;
import javax.inject.Provider;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainFragment_MembersInjector implements MembersInjector<MainFragment> {
  private final Provider<DispatchingAndroidInjector<Object>> androidInjectorProvider;

  private final Provider<ViewModelProviderFactory> providerFactoryProvider;

  private final Provider<RequestManager> requestManagerProvider;

  public MainFragment_MembersInjector(
      Provider<DispatchingAndroidInjector<Object>> androidInjectorProvider,
      Provider<ViewModelProviderFactory> providerFactoryProvider,
      Provider<RequestManager> requestManagerProvider) {
    this.androidInjectorProvider = androidInjectorProvider;
    this.providerFactoryProvider = providerFactoryProvider;
    this.requestManagerProvider = requestManagerProvider;
  }

  public static MembersInjector<MainFragment> create(
      Provider<DispatchingAndroidInjector<Object>> androidInjectorProvider,
      Provider<ViewModelProviderFactory> providerFactoryProvider,
      Provider<RequestManager> requestManagerProvider) {
    return new MainFragment_MembersInjector(androidInjectorProvider, providerFactoryProvider, requestManagerProvider);
  }

  @Override
  public void injectMembers(MainFragment instance) {
    DaggerFragment_MembersInjector.injectAndroidInjector(instance, androidInjectorProvider.get());
    injectProviderFactory(instance, providerFactoryProvider.get());
    injectRequestManager(instance, requestManagerProvider.get());
  }

  @InjectedFieldSignature("tech.sutd.pickupgame.ui.main.main.MainFragment.providerFactory")
  public static void injectProviderFactory(MainFragment instance,
      ViewModelProviderFactory providerFactory) {
    instance.providerFactory = providerFactory;
  }

  @InjectedFieldSignature("tech.sutd.pickupgame.ui.main.main.MainFragment.requestManager")
  public static void injectRequestManager(MainFragment instance, RequestManager requestManager) {
    instance.requestManager = requestManager;
  }
}
