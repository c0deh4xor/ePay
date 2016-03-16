package eripay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.base.rest.PayPalRESTException;

import eripay.domain.Purchase;
import eripay.domain.User;
import eripay.repository.UserRepository;

@Service
public class PriceService {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PurchaseService purchaseService;

  public String postPur(Purchase purchase) throws PayPalRESTException {
    //get user
    User user = userRepo.findBySignum(purchase.getSignum());
    return purchaseService.purchaseItems(user, purchase.getPrice());
  }

}
