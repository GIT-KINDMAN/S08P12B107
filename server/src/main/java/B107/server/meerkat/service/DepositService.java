package B107.server.meerkat.service;

import B107.server.meerkat.entity.Deposit;
import B107.server.meerkat.repository.DepositRepository;
import B107.server.meerkat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositService {

	private final CoinService coinService;

	private final DepositRepository depositRepository;
	private final MemberRepository memberRepository;

	/**
	 * 거래에 의한 입출금 내역
	 */
	@Transactional
	public void registDeposit(Long reqIdx, Long resIdx, Integer coin) {
		int coinInt = coin;

		// 요청자 --
		int reqCoin = coinService.findCoinById(reqIdx).getCoin();
		Deposit reqDeposit = Deposit.builder()
				.member(memberRepository.findById(reqIdx).orElse(null))
				.regDate(LocalDateTime.now())
				.coin(-coin)
				.balance(reqCoin-coinInt)
				.transactionCode(3)
				.build();
		depositRepository.save(reqDeposit);
		// 코인 내역 업데이트
		coinService.updateCoin(reqIdx, (reqCoin - coinInt));

		// 응답자 ++
		int resCoin = coinService.findCoinById(resIdx).getCoin();
		Deposit resDeposit = Deposit.builder()
				.member(memberRepository.findById(resIdx).orElse(null))
				.regDate(LocalDateTime.now())
				.coin(+coin)
				.balance(resCoin + coinInt)
				.transactionCode(3)
				.build();
		depositRepository.save(resDeposit);
		// 코인 내역 업데이트
		coinService.updateCoin(resIdx, (resCoin + coinInt));
	}

	/**
	 * 코인 충전 및 환전에 의한 입출금 내역
	 */
	@Transactional
	public void coinDeposit(Long memberIdx, int ori, int coin, int code) {
		int balance = 0;
		if(code == 1) {
			balance = ori + coin;
		} else if(code == 2) {
			balance = ori - coin;
		}

		Deposit reqDeposit = Deposit.builder()
				.member(memberRepository.findById(memberIdx).orElse(null))
				.regDate(LocalDateTime.now())
				.coin(coin)
				.balance(balance)
				.transactionCode(code)
				.build();
		depositRepository.save(reqDeposit);
	}

}
