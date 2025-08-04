package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        //1. dto -> entity로 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);

        //2. repository의 save 메소드 호출(entity를 넘겨줘야 함)
        memberRepository.save(memberEntity);

    }

    public MemberDTO login(MemberDTO memberDTO) {
    /*
    1. 회원이 입력한 이메일로 db에서 조회
    2. db에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
     */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if(byMemberEmail.isPresent()){
            //해당이메일을 가진회원이 있다
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                //비밀번호 일치
                //DTO -> Entity로 변환
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;

            }else{
                //비밀번호 불일치
                return null;
            }

        }else {
            //해당이메일을 가진 회원이 없다
            return null;
        }

    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        //memberEntityList.forEach(memberEntity -> memberDTOList.add(MemberDTO.toMemberDTO(memberEntity)));
        for(MemberEntity memberEntity : memberEntityList){
        memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;

    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }


    public void udpate(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailcheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){
            // 조회결과가 있다 -> 사용 불가
            return null;
        } else {
            // 조회결과가 없다 -> 사용 가능
            return "ok";
        }
    }
}
